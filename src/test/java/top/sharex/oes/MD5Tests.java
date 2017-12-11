package top.sharex.oes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.sharex.oes.service.CommonService;
import top.sharex.oes.util.MD5Helper;

import java.text.ParseException;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("top.sharex.oes.dao")
public class MD5Tests {
    @Autowired
    MD5Helper md5Helper;
    @Autowired
    CommonService commonService;

    @Test
    public void test(){
//        String s = "a";
//        System.out.println("原始：" + s);
//        System.out.println("MD5加密：" + md5Helper.MD5(s));
//        System.out.println("MD5加密后再加密：" + md5Helper.KL(md5Helper.MD5(s)));
//        System.out.println("解密为MD5：" + md5Helper.Decryption(md5Helper.KL(md5Helper.MD5(s))));

        //双方约定好的签名
        final String key ="123";
        //数据
        final String data ="a";

        System.out.println("原始数据为: "+data);

        //对数据进行加密处理
        String nt_data = md5Helper.encode(data, key);
        System.out.println("加密为: "+nt_data);

        //对加密后的数据进行md5处理
        System.out.println("加密后的数据进行md5加密: "+md5Helper.sign("nt_data=" + nt_data));

        //对经过md5处理后的数据进行第二次加密
        String nt_data2 = md5Helper.encode(md5Helper.sign("nt_data=" + nt_data), key);
        System.out.println("md5加密后的数据进行第二次加密: "+nt_data2);

        //对加密后的数据进行解密处理
        String sign_en = md5Helper.decode(nt_data2, key);
        System.out.println("md5及二次加密后的数据进行解密: "+sign_en);

        //直接解密
        String temp = md5Helper.decode(nt_data, key);
        System.out.println("原始数据为: "+temp);
    }

    /**
     * id_studentName_studentNum_classId_title_startTime_endTime
     */
    @Test
    public void testCommonServiceImpl() throws ParseException {
        String encode = md5Helper.encode("1_杨雁飞_1_1_Maths_20171203120000_20171203140000", "123");
        String res = commonService.isValidExamURL(encode, "123");
        System.out.println(res);
    }
}
