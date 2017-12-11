package top.sharex.oes.service.impl;

import org.springframework.stereotype.Component;
import top.sharex.oes.dao.*;
import top.sharex.oes.domain.Exam;
import top.sharex.oes.domain.QuestionInMemory;
import top.sharex.oes.entity.Choice;
import top.sharex.oes.entity.ExamResult;
import top.sharex.oes.entity.Question;
import top.sharex.oes.entity.Student;
import top.sharex.oes.service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @ author: Crystal
 * @ time:  17/12/09.
 **/
@Component
public class StudentServiceImpl implements StudentService {

    ExamQuestionsPoolMapper examQuestionsPoolMapper;
    QuestionMapper questionMapper;
    ChoiceMapper choiceMapper;
    ExamMapper examMapper;
    ExamResultMapper examResultMapper;


    @Override
    public Exam generateExamFromPool(String poolName, Integer studentId) {

        Integer poolId = examQuestionsPoolMapper.selectQuestionPoolByPoolName(poolName).getId();
        List<Question> singleQuestionList = questionMapper.selectByPoolIdAndType(poolId,(byte)0);
        List<Question> multiQuestionList = questionMapper.selectByPoolIdAndType(poolId,(byte)1);

        //poolName和title都是课程名
        top.sharex.oes.entity.Exam exam = examMapper.selectByTitleLatest(poolName).get(0);
        int singleNum = exam.getSingleNum();
        int multiNum = exam.getMultiNum();

        List<Question> singleRes = getRandomList(singleQuestionList, singleNum);
        List<Question> multiRes = getRandomList(multiQuestionList, multiNum);
        singleRes.addAll(multiRes);

        List<QuestionInMemory> questionInMemoryList = new ArrayList<>();
        List<top.sharex.oes.domain.Choice> choiceInMemoryList = new ArrayList<>();
        for(Question question:singleRes){
            List<Choice> choiceList = choiceMapper.selectChoicesByQuestionId(question.getId());

            for(Choice choice : choiceList){
                top.sharex.oes.domain.Choice choiceInMemory = new top.sharex.oes.domain.Choice();
                choiceInMemory.setChoiceId(choice.getId());
                choiceInMemory.setContent(choice.getContent());
                choiceInMemory.setIsChoosed(false);
                if(choice.getIscorrect() == 1){
                    choiceInMemory.setIsCorrect(true);
                }else{
                    choiceInMemory.setIsCorrect(false);
                }
                choiceInMemoryList.add(choiceInMemory);

            }

            QuestionInMemory questionInMemory = new QuestionInMemory();
            questionInMemory.setQuestionId(question.getId());
            questionInMemory.setContent(question.getContent());
            questionInMemory.setType(question.getType());
            questionInMemory.setScore(question.getScore());
            questionInMemory.setChoices(choiceInMemoryList);
            questionInMemory.setAnswered(false);
            questionInMemory.setMarked(false);

            questionInMemoryList.add(questionInMemory);
        }

        //domain里面创建exam
        Exam examFinal = new Exam();
        examFinal.setExamId(exam.getId());
        examFinal.setTitle(poolName);
        examFinal.setQuestionInMemoryList(questionInMemoryList);
        examFinal.setStudentId(studentId);

        return examFinal;
    }

    @Override
    public void markOrCancelMarkQuestion(QuestionInMemory questionInMemory) {
        if(questionInMemory.getMarked()){
            questionInMemory.setMarked(false);
        }else {
            questionInMemory.setMarked(true);
        }

    }

    /**
     * 保存每个问题的答题情况，每次选择答案之后执行
     *
     * @param choiceInMemory domain里面的question
     */
    @Override
    public void saveQuestionResult(QuestionInMemory questionInMemory, top.sharex.oes.domain.Choice choiceInMemory) {

        List<top.sharex.oes.domain.Choice> choices = questionInMemory.getChoices();
        //单选题：选中的只有一个
        if(questionInMemory.getType() == (byte)0){
            for(top.sharex.oes.domain.Choice choice:choices){
                if(choice.getChoiceId() == choiceInMemory.getChoiceId()){
                    choice.setIsChoosed(true);
                }else{
                    choice.setIsChoosed(false);
                }
            }
        }else {//多选题：选中的有多个
            if(choiceInMemory.getIsChoosed()){
                choiceInMemory.setIsChoosed(false);
            }else{
                choiceInMemory.setIsChoosed(true);
            }
        }


    }

    @Override
    public List<QuestionInMemory> getExamChoiceResults(Exam exam) {
        return exam.getQuestionInMemoryList();
    }

    @Override
    public void submit(Exam exam) {
        List<QuestionInMemory> questionInMemoryList = exam.getQuestionInMemoryList();
        int totalScore = 0;
        for(QuestionInMemory questionInMemory:questionInMemoryList){
            List<top.sharex.oes.domain.Choice> choices = questionInMemory.getChoices();
            //单选题
            if(questionInMemory.getType() == (byte)0){
                for(top.sharex.oes.domain.Choice choiceInMemory:choices){
                    if(choiceInMemory.getIsChoosed() && choiceInMemory.getIsCorrect()){
                        totalScore+=questionInMemory.getScore();
                    }
                }
            }else {//多选题
                List<top.sharex.oes.domain.Choice> choseChoices = new ArrayList<>();
                boolean isAllCorrect = true;
                for(top.sharex.oes.domain.Choice choiceInMemory:choices){
                    //选择了的选项
                    if(choiceInMemory.getIsChoosed()){
                        choseChoices.add(choiceInMemory);
                    }
                }
                for(top.sharex.oes.domain.Choice choice:choseChoices){
                    if(!(choice.getIsCorrect())){
                        isAllCorrect = false;
                        break;
                    }
                }
                if(isAllCorrect){
                    totalScore+=questionInMemory.getScore();
                }

            }

        }

        ExamResult examResult = new ExamResult();
        examResult.setExamId(exam.getExamId());
        examResult.setExamText(exam.getTitle());
        examResult.setScore(totalScore);
        examResult.setStudentId(exam.getStudentId());

        examResultMapper.insert(examResult);
    }

    /**
     * 获取 在考试前给学生确认的信息
     *
     * @param stuId 学生id
     * @param exam  考试id
     */
    @Override
    public String getInfoBeforeExamToComfirm(Integer stuId, Integer exam) {
        return null;
    }

    @Override
    public boolean confirmInfoBeforeExam(Student stuId, Integer examId) {
        return false;
    }

    /**
     * 考试开始(获取一系列)
     *
     * @param stuId
     * @param examId
     * @return true确认开始考试  false未确认
     */
    @Override
    public boolean BeginExam(Integer stuId, Integer examId) {
        return false;
    }

    /**
     * @function:从list中随机抽取若干不重复元素
     *
     * @param paramList:被抽取list
     * @param count:抽取元素的个数
     * @return:由抽取元素组成的新list
     */
    private List<Question> getRandomList(List<Question> paramList,int count){
        if(paramList.size()<count){
            return paramList;
        }
        Random random=new Random();
        List<Integer> tempList=new ArrayList<Integer>();
        List<Question> newList=new ArrayList<Question>();
        int temp=0;
        for(int i=0;i<count;i++){
            temp=random.nextInt(paramList.size());//将产生的随机数作为被抽list的索引
            if(!tempList.contains(temp)){
                tempList.add(temp);
                newList.add(paramList.get(temp));
            }
            else{
                i--;
            }
        }
        return newList;
    }
}
