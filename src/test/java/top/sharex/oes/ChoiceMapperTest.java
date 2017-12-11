package top.sharex.oes;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.sharex.oes.dao.ChoiceMapper;
import top.sharex.oes.dao.ExamMapper;
import top.sharex.oes.entity.Choice;
import top.sharex.oes.entity.Exam;

import java.util.List;

/**
 * @author danielyang
 * @Date 2017/11/9 17:37
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("top.sharex.oes.dao")
public class ChoiceMapperTest {
    @Autowired
    ChoiceMapper mapper;
    @Autowired
    ExamMapper examMapper;

    @Test
    public void testSelectByClazzId() {
        List<Choice> pools = mapper.selectChoicesByQuestionId(3);
        System.out.println(new Gson().toJson(pools));
    }

    @Test
    public void testSelectByTitleLatest(){
        Exam exam = examMapper.selectByTitleLatest("数学").get(0);
        System.out.println(new Gson().toJson(exam));
    }

}
