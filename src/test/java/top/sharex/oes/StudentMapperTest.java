package top.sharex.oes;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.sharex.oes.dao.StudentMapper;
import top.sharex.oes.entity.Student;

import java.util.List;

/**
 * @author danielyang
 * @Date 2017/11/9 17:37
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("top.sharex.oes.dao")
public class StudentMapperTest {
    @Autowired
    StudentMapper mapper;

    @Test
    public void testSelectByClazzId(){
        List<Student> students = mapper.selectListByClazzId(2);
        System.out.println(new Gson().toJson(students));
    }

}
