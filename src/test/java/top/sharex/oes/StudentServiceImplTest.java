package top.sharex.oes;

import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.sharex.oes.domain.Choice;
import top.sharex.oes.domain.Exam;
import top.sharex.oes.domain.QuestionInMemory;
import top.sharex.oes.service.StudentService;

import java.util.List;

/** 
* StudentServiceImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>Dec 11, 2017</pre> 
* @version 1.0 
*/

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("top.sharex.oes.dao")
public class StudentServiceImplTest { 

    @Autowired
    StudentService studentService;

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: generateExamFromPool(String poolName, Integer studentId) 
* 
*/ 
@Test
public void testGenerateExamFromPool() throws Exception { 
    Exam exam = studentService.generateExamFromPool("测试", 1);
    System.out.println(new Gson().toJson(exam));
} 

/** 
* 
* Method: markOrCancelMarkQuestion(QuestionInMemory questionInMemory) 
* 
*/ 
@Test
public void testMarkOrCancelMarkQuestion() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: saveQuestionResult(QuestionInMemory questionInMemory, top.sharex.oes.domain.Choice choiceInMemory) 
* 
*/ 
@Test
public void testSaveQuestionResult() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getExamChoiceResults(Exam exam) 
* 
*/ 
@Test
public void testGetExamChoiceResults() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: submit(Exam exam) 
* 
*/ 
@Test
public void testSubmit() throws Exception {
    Exam exam = studentService.generateExamFromPool("测试", 2);
    List<QuestionInMemory> questionInMemoryList = exam.getQuestionInMemoryList();
    for(QuestionInMemory questionInMemory:questionInMemoryList){
        List<Choice> choices = questionInMemory.getChoices();
        Choice choice0 = choices.get(0);
        studentService.saveQuestionResult(questionInMemory, choice0);
    }
    studentService.submit(exam);
} 

/** 
* 
* Method: getInfoBeforeExamToComfirm(Integer stuId, Integer exam) 
* 
*/ 
@Test
public void testGetInfoBeforeExamToComfirm() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: confirmInfoBeforeExam(Student stuId, Integer examId) 
* 
*/ 
@Test
public void testConfirmInfoBeforeExam() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: BeginExam(Integer stuId, Integer examId) 
* 
*/ 
@Test
public void testBeginExam() throws Exception { 
//TODO: Test goes here... 
} 


/** 
* 
* Method: getRandomList(List<Question> paramList, int count) 
* 
*/ 
@Test
public void testGetRandomList() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = StudentServiceImpl.getClass().getMethod("getRandomList", List<Question>.class, int.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

} 
