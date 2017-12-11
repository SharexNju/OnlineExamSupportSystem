package top.sharex.oes.service;

import java.text.ParseException;

/**
 * Created by I332396 on 11/21/2017.
 */
public interface CommonService {
    /**
     * 生成考试密钥
     * @param initStr
     * @param key
     * @return
     */
    String generateExamURL(String initStr, String key);

    /**
     * 发送包含密钥的邮件
     *
     * @param examURL 考试密钥 generateEmailURL的结果string
     */
    void sendExamURLByEmail(String examURL, String emailAddress, String subject);

    /**
     * 验证密钥
     *
     * @param result  考试密钥
     * @param key     双方约定的key
     * @return
     */
    String isValidExamURL(String result, String key) throws ParseException;

}
