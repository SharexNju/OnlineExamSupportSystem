package top.sharex.oes.core.session;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * OESSession Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>ʮһ�� 21, 2017</pre>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("top.sharex.oes.dao")
public class OESSessionTest {
    @Autowired
    OESSessionManager sessionManager;

    @Before
    public void before() throws Exception {
        sessionManager.getSession("sid");
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: newSession(String name, OESScheduler scheduler)
     */
    @Test
    public void testNewSession() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: put(String id, Object object)
     */
    @Test
    public void testPutForIdObject() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: put(String id, Object object, long timeExpire)
     */
    @Test
    public void testPutForIdObjectTimeExpire() throws Exception {
        OESSession session = sessionManager.getSession("sid");
        session.put("a", "abc", 1000);
        Thread.sleep(900);
        session.touch("a");
        Thread.sleep(900);
        System.out.println(session.get("a"));
    }

    /**
     * Method: touch(String id)
     */
    @Test
    public void testTouch() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: get(String id, Class<T> tClass)
     */
    @Test
    public void testGetForIdTClass() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: get(String id)
     */
    @Test
    public void testGetId() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: remove(String id)
     */
    @Test
    public void testRemove() throws Exception {
//TODO: Test goes here... 
    }

}


