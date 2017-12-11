package top.sharex.oes;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.sharex.oes.dao.ClazzMapper;
import top.sharex.oes.entity.Clazz;

import java.util.List;

/**
 * @author danielyang
 * @Date 2017/11/9 17:27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("top.sharex.oes.dao")
public class ClazzMapperTest {
    @Autowired
    ClazzMapper mapper;

    @Test
    public void testSelectByTeacherId() {
        List<Clazz> clazzList = mapper.selectClazzByTeacherId(1);
        System.out.println(new Gson().toJson(clazzList));
    }
}
