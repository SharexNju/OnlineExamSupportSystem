package top.sharex.oes.core;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * SessionDemo Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>ʮһ�� 21, 2017</pre>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("top.sharex.oes.dao")
public class SessionDemoTest {
    @Autowired
    SessionDemo sessionDemo;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: test()
     */
    @Test
    public void testTest() throws Exception {
        sessionDemo.test();
    }


} 
