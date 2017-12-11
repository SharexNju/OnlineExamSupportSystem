package top.sharex.oes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.sharex.oes.util.EmailHelper;

import javax.mail.MessagingException;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("top.sharex.oes.dao")
public class OesApplicationTests {
    @Autowired
    EmailHelper helper;

    @Test
    public void contextLoads() {
        try {
            helper.sendEmail("1357627989@qq.com", "测试", "这是一封测试邮件");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
