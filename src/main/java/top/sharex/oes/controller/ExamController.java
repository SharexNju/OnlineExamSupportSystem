package top.sharex.oes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.sharex.oes.service.StudentService;

/**
 * @ author: 141250153 邢程
 * @ time:  17/11/15.
 **/

@Controller
@RequestMapping("/exam")
public class ExamController {
    @Autowired
    StudentService studentService;

    @RequestMapping("/getInfoBeforeExam")
    @ResponseBody
    public String getInfoBeforeExam(@RequestParam Integer stuId,@RequestParam Integer examId){
       // studentService.getInfoBeforeExamToComfirm(stuId,examId);;
        return null;
    }

    @RequestMapping("/confirmInfo")
    @ResponseBody
    public boolean confirmInfo(){
        return  true;
    }

//    @RequestMapping("/beginExam")
//    @ResponseBody
//    public String beginE
}
