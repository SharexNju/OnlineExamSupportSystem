package top.sharex.oes;

import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.sharex.oes.dao.ExamResultMapper;

/**
 *
 * Created by Crystal on 11/12/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("top.sharex.oes.dao")
public class ExamResultMapperTest {

    @Autowired
    ExamResultMapper mapper;

//    @Test
//    public void testInsert(){
//
//
//        ExamResult examResult = new ExamResult();
//        examResult.setExamId(3);
//        examResult.setStudentId(1);
//        examResult.setScore(14);
//        examResult.setExamText("测试");
//        System.out.println(new Gson().toJson(examResult));
//        mapper.insert(examResult);
//    }
}
