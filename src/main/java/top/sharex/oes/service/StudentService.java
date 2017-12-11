package top.sharex.oes.service;

import top.sharex.oes.domain.Choice;
import top.sharex.oes.domain.Exam;
import top.sharex.oes.domain.QuestionInMemory;
import top.sharex.oes.entity.Student;

import java.util.List;


/**学生模块服务
 * *********************Pay Attention Pls: 这里涉及的很多实体，请明确区分属于数据库还是内存********************
 * Created by Crystal on 11/14/2017.
 */
public interface StudentService {

    /**
     * 按照考试设置，获得相应数量的试题，随机排列，保存到domain的exam里面
     * @param poolName
     * @return
     */
    Exam generateExamFromPool(String poolName, Integer studentId);

    /**
     * 标记/取消标记问题
     * @param questionInMemory
     */
    void markOrCancelMarkQuestion(QuestionInMemory questionInMemory);

    /**
     * 保存每个问题的答题情况，每次选择答案之后执行
     * @param choiceInMemory domain里面的choice
     */
    void saveQuestionResult(QuestionInMemory questionInMemory, Choice choiceInMemory);

    /**
     * 获得内存里面的实时答题结果
     * @param exam
     * @return
     */
    List<QuestionInMemory> getExamChoiceResults(Exam exam);

    /**
     * 确认提交，保存试题结果到数据库examResult
     * @param exam
     */
    void submit(Exam exam);


    /**
     * 获取 在考试前给学生确认的信息
     * @param stuId 学生id
     * @param exam 考试id
     */
    String  getInfoBeforeExamToComfirm(Integer stuId,Integer exam);

    /**
     * 确认 考试前的学生信息
     * @param stuId 学生id
     * @param examId 考试id
     * @return true确认信息正确  false未确认正确
     */
    boolean confirmInfoBeforeExam(Student stuId,Integer examId);

    /**
     * 考试开始(获取一系列)
     * @param stuId
     * @param examId
     * @return true确认开始考试  false未确认
     */
    boolean BeginExam(Integer stuId,Integer examId);



}
