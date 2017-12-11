package top.sharex.oes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.sharex.oes.service.CommonService;
import top.sharex.oes.util.EmailHelper;
import top.sharex.oes.util.MD5Helper;

import javax.mail.MessagingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: Crystal
 * @date: 11/21/2017
 * @description:
 */
@Component
public class CommonServiceImpl implements CommonService{

    @Autowired
    MD5Helper md5Helper;
    @Autowired
    EmailHelper emailHelper;

    /**
     * 生成考试密钥 假设特定str生成
     *
     * @param initStr 原始string
     * @param key     双方约定的key
     */
    @Override
    public String generateExamURL(String initStr, String key) {
        //数据进行第一次加密
        String firstEncode = md5Helper.encode(initStr, key);
        //一次加密后的数据进行MD5加密
//        String md5Encode = md5Helper.sign("nt_data=" + firstEncode);
        //MD5加密后的数据进行第二次加密
//        String result = md5Helper.encode(md5Encode, key);

        return firstEncode;
    }

    /**
     * 发送包含密钥的邮件
     *
     * @param examURL 考试密钥 generateEmailURL的结果string
     */
    @Override
    public void sendExamURLByEmail(String examURL, String emailAddress, String subject) {
        try {
            emailHelper.sendEmail(emailAddress, "考试密钥", examURL);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 验证密钥
     * 原始字符串格式：id_studentName_studentNum_classId_title_startTime_endTime
     *
     * @param result  考试密钥
     * @param key     双方约定的key
     * @return
     */
    @Override
    public String isValidExamURL(String result, String key) throws ParseException {
        String decode = md5Helper.decode(result, key);

        String[] infos = decode.split("_");
        String endTime = infos[6];
        String formatEndTime = endTime.substring(0,4)+"-"+endTime.substring(4,6)+"-"+endTime.substring(6,8)+" "
                +endTime.substring(8,10)+":"+endTime.substring(10, 12)+":"+endTime.substring(12);
        System.out.println(formatEndTime);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String systemTime = df.format(new Date());// new Date()为获取当前系统时间
        System.out.println(systemTime);

        Date formatEnd = df.parse(formatEndTime);
        Date formatSys = df.parse(systemTime);
        long l = formatSys.getTime() - formatEnd.getTime();
        if(l <= 0){//密钥有效
            return result;
        }else{
            return "Error";
        }

    }


}
