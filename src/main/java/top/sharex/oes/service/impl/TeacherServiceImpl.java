package top.sharex.oes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.sharex.oes.dao.ExamMapper;
import top.sharex.oes.dao.ExamQuestionsPoolMapper;
import top.sharex.oes.dao.StudentMapper;
import top.sharex.oes.entity.Exam;
import top.sharex.oes.entity.ExamQuestionsPool;
import top.sharex.oes.entity.ExamResult;
import top.sharex.oes.entity.Student;
import top.sharex.oes.service.TeacherService;
import top.sharex.oes.util.EmailHelper;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.List;

/**
 * @author: Crystal
 * @date: 11/14/2017
 * @description: 教师模块service impl
 */

@Component
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    EmailHelper emailHelper;

    @Autowired
    ExamMapper examMapper;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    ExamQuestionsPoolMapper examQuestionsPoolMapper;

    /**
     * 根据班级选择考生
     *
     * @param classId 班级
     * @return List<Student>
     */
    @Override
    public List<Student> chooseByClassId(Integer classId) {
        return studentMapper.selectListByClazzId(classId);
    }

    /**
     * 根据学生号选择考生
     *
     * @param stuId 学号
     * @return
     */
    @Override
    public Student chooseByStuId(Integer stuId) {
        return studentMapper.selectByPrimaryKey(stuId);
    }

    /**
     * 根据科目选择试题池
     *
     * @param poolName 科目名称
     * @return
     */
    @Override
    public ExamQuestionsPool chooseQuestionPool(String poolName) {
        return examQuestionsPoolMapper.selectQuestionPoolByPoolName(poolName);
    }

    /**
     * 设置本次考试相关属性
     *
     * @param title      标题
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @param singleNum  单选题数量
     * @param multiNum   多选题数量
     * @param totalScore 总分
     * @return 注：单选和多选的分值确定，不由教师设置
     */
    @Override
    public void configExam(String title, Integer singleNum, Integer multiNum, Integer totalScore, Date startTime, Date endTime) {
        Exam exam = new Exam(title, singleNum, multiNum, totalScore, startTime, endTime);
        examMapper.insert(exam);
    }


//    /**
//     * 统计本场考试成绩 从里来
//     *
//     * @param examId 考试ID
//     * @return
//     */
//    @Override
//    public List<ExamResult> caculateScores(Integer examId) {
//
//        return null;
//    }

    /**
     * 邮件发送考试成绩
     *
     * @param examResultList 考试成绩list
     */
    @Override
    public void sendResultByEmail(List<ExamResult> examResultList) {
        for(ExamResult examResult:examResultList){
            Integer stuId = examResult.getStudentId();
            Student student = studentMapper.selectByPrimaryKey(stuId);
            try {
                emailHelper.sendEmail(student.getStudentEmail(), examResult.getExamText()+" exam result",
                        "Your score is: "+examResult.getScore());
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }
}
