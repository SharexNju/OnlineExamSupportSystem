package top.sharex.oes.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.sharex.oes.core.session.OESSession;
import top.sharex.oes.core.session.OESSessionManager;

/**
 * @author danielyang
 * @Date 2017/11/21 16:20
 */
@Component
public class SessionDemo {
    //自动注入的sessionManager
    @Autowired
    OESSessionManager sessionManager;

    /**
     * 使用的示例代码
     */
    public void test() throws Exception {
        //通过一个sid获得一个session，如果session不存在会自动创建
        //通常是一个用户一个sid，建议使用能区分用户的唯一标识
        OESSession session = sessionManager.getSession("sid");

        //存入一个对象
        session.put("name", "Daniel");
        //获得一个对象
        String name = session.get("name", String.class);

        //存入一个对象，设置过期时间为1秒
        session.put("int", 1, 1000);

        Thread.sleep(1200);

        //这里已经过期，无法取得
        Integer i = session.get("int", Integer.class);

        //存入一个对象，设置过期时间为1秒
        session.put("int", 1, 1000);

        Thread.sleep(600);
        //重新进行过期计时
        session.touch("int");
        Thread.sleep(600);

        //这时候可以获得
        i = session.get("int", Integer.class);

        sessionManager.deleteSession("sid");

    }

}
