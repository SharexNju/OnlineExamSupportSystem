package top.sharex.oes.controller;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import top.sharex.oes.dao.ClazzMapper;
import top.sharex.oes.dao.StudentMapper;
import top.sharex.oes.entity.Clazz;
import top.sharex.oes.entity.Student;
import top.sharex.oes.util.DownloadHelper;
import top.sharex.oes.util.ErrorCodeEnum;
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
@RequestMapping("/classAndStudent")
public class ClassStudentController {
    Logger logger = LoggerFactory.getLogger(ClassStudentController.class);
    @Autowired
    DownloadHelper downloadHelper;

    @Autowired
    ClazzMapper clazzMapper;

    @Autowired
    StudentMapper studentMapper;

    private final static String templateFileName = "students_example.xls";

    @RequestMapping("/allClazz")
    public String teachAllClazz(Model model, @RequestParam("teachId") Integer teachId) {
        List<Clazz> clazzes = clazzMapper.selectClazzByTeacherId(teachId);
        if (clazzes == null)
            clazzes = new ArrayList<>();
        model.addAttribute("clazzList", clazzes);
        return "all_clazz";
    }

    @RequestMapping("/specificClazz")
    public String specificClazz(Model model, @RequestParam("clazzId") Integer clazzId) {
        Clazz clazz = clazzMapper.selectByPrimaryKey(clazzId);
        if (clazz == null) {
            logger.info("找不到班级:classId = ?", clazzId);
            model.addAttribute("error_mess", "找不到班级");
        }
        List<Student> students = studentMapper.selectListByClazzId(clazzId);
        if (students == null)
            students = new ArrayList<>();
        model.addAttribute("studentList", students);
        return "specific_clazz";
    }

    @RequestMapping("/template")
    public void downloadTemplate(HttpServletResponse response) {
        downloadHelper.download(response, templateFileName);
    }

    @RequestMapping("/upload")
    @ResponseBody
    public ResponseVO uploadQuestions(@RequestParam("classId") Integer classId,
                                      @RequestParam("uploadFile") MultipartFile file) {
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
                String name = cells[0].getContents();
                if (name == null || name.length() == 0)
                    break;
                String studentNum = cells[1].getContents();
                String email = cells[2].getContents();
                Boolean isMale = "1".equals(cells[3].getContents());

                Student student = new Student();
                student.setClassId(classId);
                student.setIsMale(isMale);
                student.setStudentNum(studentNum);
                student.setStudentEmail(email);
                student.setStudentName(name);
                try {
                    studentMapper.insert(student);
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
