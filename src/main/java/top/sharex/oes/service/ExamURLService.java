package top.sharex.oes.service;

import top.sharex.oes.entity.Exam;

/**
 * @ author: 141250153 邢程
 * @ time:  17/11/15.
 **/
public interface ExamURLService {
    /**
     * 生成考试密钥
     * 所有考生的密钥前缀一致,将学号等个人信息加密
     * @param initStr 原始string 学号
     * @param exam Exam
     * @return 加密的字符串(包含 学号,考试id,考试开始时间,考试结束时间)
     *
     */
    String generateExamURL(String initStr, Exam exam);


    /**
     * 验证密钥是否有效
     * @param result 考生的考试密钥
     * @return true验证通过  信息错误  超过考试时间
     */
    boolean isValidExamURL(String result);

    /**
     * 发送包含密钥的邮件
     * @param examURL 考试密钥 generateExamURL的结果
     */
    void sendExamURLByEmail(String examURL, String emailAddress, String subject);




}
