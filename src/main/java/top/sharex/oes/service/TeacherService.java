package top.sharex.oes.service;

import top.sharex.oes.entity.ExamQuestionsPool;
import top.sharex.oes.entity.ExamResult;
import top.sharex.oes.entity.Student;

import java.util.Date;
import java.util.List;

/**教师模块服务
 * Created by Crystal on 11/14/2017.
 */
public interface TeacherService {
    /**
     * 根据班级选择考生
     * @param classId 班级
     * @return
     */
    List<Student> chooseByClassId(Integer classId);

    /**
     * 根据学生号选择考生
     * @param stuId 学号
     * @return
     */
    Student chooseByStuId(Integer stuId);

    /**
     * 根据科目选择试题池
     * @param poolName 科目名称
     * @return
     */
    ExamQuestionsPool chooseQuestionPool(String poolName);

    /**
     * 设置本次考试相关属性
     * @param title 标题
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param singleNum 单选题数量
     * @param multiNum 多选题数量
     * @param totalScore 总分
     * @return
     *
     * 注：单选和多选的分值确定，不由教师设置
     */
    void configExam(String title, Integer singleNum, Integer multiNum, Integer totalScore, Date startTime, Date endTime);


//    /**
//     * 统计本场考试成绩
//     * @param examId 考试ID
//     * @return
//     */
//    List<ExamResult> caculateScores(Integer examId);

    /**
     * 邮件发送考试成绩
     * @param examResultList 考试成绩list
     */
    void sendResultByEmail(List<ExamResult> examResultList);
}
