package top.sharex.oes.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.sharex.oes.config.EmailConfig;
import top.sharex.oes.dao.ClazzMapper;
import top.sharex.oes.dao.TeacherMapper;

/**
 * @author danielyang
 * @Date 2017/10/26 13:25
 */
@Controller
@RequestMapping("/hello")
public class HelloController {
    private static Logger logger = LoggerFactory.getLogger(HelloController.class);
    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    EmailConfig config;

    @RequestMapping("/hi")
    @ResponseBody
    public String sayHello(@RequestParam String name) {
        logger.info(teacherMapper.selectByPrimaryKey(1).getTeacherName());
        logger.info("log info");
        return "你好 " + name;
    }

    @RequestMapping("/template")
    public String templateTest(Model model, @RequestParam String name) {
        logger.info("in");
        model.addAttribute("name", name);
        return "test";
    }

}
