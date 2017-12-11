package top.sharex.oes.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.sharex.oes.dao.ClazzMapper;
import top.sharex.oes.dao.ExamQuestionsPoolMapper;
import top.sharex.oes.dao.StudentMapper;
import top.sharex.oes.entity.Clazz;
import top.sharex.oes.entity.ExamQuestionsPool;
import top.sharex.oes.entity.Student;
import top.sharex.oes.service.CommonService;
import top.sharex.oes.service.TeacherService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Crystal
 *
 */
@Controller
@RequestMapping("/examManagement")
public class ExamManageController {

    Logger logger = LoggerFactory.getLogger(ExamManageController.class);

    @Autowired
    TeacherService teacherService;
    @Autowired
    CommonService commonService;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    ClazzMapper clazzMapper;
    @Autowired
    ExamQuestionsPoolMapper examQuestionsPoolMapper;

    /**
     * 获得该教师的所有课程
     * @param model
     * @param teacherId
     * @return
     */
    @RequestMapping("/selectCourse")
    public String getCourse(Model model, @RequestParam("teachId") Integer teacherId){
        List<ExamQuestionsPool> examQuestionsPools = examQuestionsPoolMapper.selectQuestionPoolByTeacherId(teacherId);
        model.addAttribute("examQuestionList", examQuestionsPools);
        return "exam_Question_List";
    }
    /**
     * 新增考试，配置考试参数
     * @param course
     * @param singleNum
     * @param multiNum
     * @param totalScore
     * @param startTime
     * @param endTime
     * @return
     * @throws ParseException
     */
    @RequestMapping("/addExam")
    public String addExam(@RequestParam("course") String course, @RequestParam("singleNum") Integer singleNum,
                          @RequestParam("multiNum") Integer multiNum, @RequestParam("totalScore") Integer totalScore, @RequestParam("startTime") String startTime,
                          @RequestParam("endTime") String endTime) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        teacherService.configExam(course, singleNum, multiNum, totalScore, df.parse(startTime), df.parse(endTime));

        return "add_Exam";
    }

    /**
     * 根据考试班级获得考生列表
     * @param model
     * @param classId
     * @return
     */
    @RequestMapping("/getStudents")
    public String getAllStudent(Model model, @RequestParam("classId") Integer classId){
        Clazz clazz = clazzMapper.selectByPrimaryKey(classId);
        if (clazz == null) {
            logger.info("找不到班级:classId = ?", classId);
            model.addAttribute("error_mess", "找不到班级");
        }
        List<Student> studentList = studentMapper.selectListByClazzId(classId);
        if(studentList == null){
            studentList = new ArrayList<>();
        }
        model.addAttribute("studentList", studentList);

        return "get_Students";

    }

    /**
     * 根据考生列表给每位考生发送包含考试密钥的邮件
     * 原始字符串 id_studentName_studentNum_classId_title_startTime_endTime
     * @param students, title, startTime, endTime(格式：yyyyMMddHHmmss)
     * @return
     */
    @RequestMapping("sendEmail")
    public String sendEmails(@RequestParam("studentList") List<Student> students, @RequestParam("title") String title,
                             @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime){
       String key = "123";
        for(Student student:students){
            String initStr = student.getId()+"_"+student.getStudentName()+"_"+student.getStudentNum()
                    +"_"+student.getClassId()+"_"+title+"_"+startTime+"_"+endTime;
            String examURL = commonService.generateExamURL(initStr, key);
            commonService.sendExamURLByEmail(examURL, student.getStudentEmail(), "考试地址");
        }

        return "send_Email";
    }


}
