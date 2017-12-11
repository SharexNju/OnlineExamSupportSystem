package top.sharex.oes.controller;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import top.sharex.oes.dao.ChoiceMapper;
import top.sharex.oes.dao.ExamQuestionsPoolMapper;
import top.sharex.oes.dao.QuestionMapper;
import top.sharex.oes.entity.Choice;
import top.sharex.oes.entity.ExamQuestionsPool;
import top.sharex.oes.entity.Question;
import top.sharex.oes.util.DownloadHelper;
import top.sharex.oes.util.ErrorCodeEnum;
import top.sharex.oes.util.Pair;
import top.sharex.oes.vo.ResponseVO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author danielyang
 * @Date 2017/11/1 17:14
 */
@Controller
@RequestMapping("/questionAndPool")
public class QuestionAndPoolController {
    @Autowired
    DownloadHelper downloadHelper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    ExamQuestionsPoolMapper examQuestionsPoolMapper;

    @Autowired
    ChoiceMapper choiceMapper;


    private String templateFileName = "question_example.xls";

    @RequestMapping("/allpool")
    public String allQuestionPool(Model model, @RequestParam Integer teacherId) {
        List<ExamQuestionsPool> poolList = examQuestionsPoolMapper
                .selectQuestionPoolByTeacherId(teacherId);
        model.addAttribute("poolList", poolList);
        return "allpool";
    }

    @RequestMapping("specificPool")
    public String specificPool(Model model, @RequestParam Integer poolId) {
        List<Pair<Question, List<Choice>>> modelData = new ArrayList<>();
        List<Question> questionList = questionMapper.selectByPoolId(poolId);
        for (Question question : questionList) {
            List<Choice> choices = choiceMapper.selectChoicesByQuestionId(question.getId());
            modelData.add(Pair.newInstance(question, choices));
        }
        model.addAttribute("questions", modelData);
        return "specificPool";
    }

    @RequestMapping("/template")
    public void downloadTemplate(HttpServletResponse response) {
        downloadHelper.download(response, templateFileName);
    }

    @RequestMapping("/upload")
    public ResponseVO uploadQuestions(@RequestParam("courseId") Integer courseId, @RequestParam
            ("uploadFile")
            MultipartFile file) {
        try {
            Workbook workbook = Workbook.getWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheet(0);
            if (sheet == null)
                return ResponseVO.newInstance(ErrorCodeEnum.PARAMETER_ERROR);
            int successNum = 0;
            //遍历行
            for (int i = 1; i < sheet.getRows(); i++) {
                //遍历每一行的每个单元格
                Cell[] cells = sheet.getRow(i);
                String des = cells[0].getContents();
                if (des == null || des.length() == 0)
                    break;
                String questionType = cells[1].getContents();
                String questionScore = cells[2].getContents();
                String answerNum = cells[3].getContents();

                try {
                    Question question = new Question();
                    question.setPoolId(courseId);
                    question.setContent(des);
                    question.setScore(Integer.valueOf(questionScore));
                    question.setType(Byte.valueOf(questionType));
                    questionMapper.insert(question);
                    Integer answerNumInt = Integer.valueOf(answerNum);
                    String[] rightList = cells[4 + answerNumInt].getContents().split("[，,]");
                    List<Integer> rightIndex = new ArrayList<>();
                    for (String r : rightList) {
                        rightIndex.add(Integer.valueOf(r));
                    }

                    int j = 0;
                    for (; j < answerNumInt; j++) {
                        String content = cells[4 + j].getContents();
                        Choice choice = new Choice();
                        choice.setQuestionId(question.getId());
                        if (rightIndex.contains(j + 1))
                            choice.setIscorrect((byte) 1);
                        else
                            choice.setIscorrect((byte) 0);
                        choice.setContent(content);
                        choiceMapper.insert(choice);
                    }
                    successNum++;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            return ResponseVO.newInstance(successNum);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        return ResponseVO.newInstance(ErrorCodeEnum.UNKNOWN_ERROR);
    }


}
