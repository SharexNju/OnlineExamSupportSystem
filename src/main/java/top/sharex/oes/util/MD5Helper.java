package top.sharex.oes.util;

import org.springframework.stereotype.Component;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * implement of MD5 encryption and decryption
 * Created by Crystal on 01/11/2017.
 */
@Component
public class MD5Helper {

    //正则表达式，用于匹配
    private final static Pattern pattern = Pattern.compile("\\d+");

    private final static String charset="utf-8";
    /**
     * MD5加密字符串
     * @param initText
     * @return 加密后的字符串
     */
    public String MD5(String initText){

        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");

        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = initText.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for(int i = 0;i<charArray.length;i++){
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5bytes = md5.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();

        for(int i = 0;i<md5bytes.length;i++){
            int val = md5bytes[i] & 0xff;
            if(val < 16){
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }

    /**
     * 加密MD5加密后的字符串
     * @param md5Text
     * @return
     */
    public String KL(String md5Text){

        char[] chars = md5Text.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (chars[i] ^ 't');
        }
        String s = new String(chars);
        return s;
    }

    /**
     * 解密二次加密的字符串
     * @param str
     * @return
     */
    public String Decryption(String str){

        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (chars[i] ^ 't');
        }
        String s = new String(chars);
        return s;
    }

    /**
     * 加密处理
     * @param src 需要加密的字符串
     * @param key 用于加密的值
     * @return
     */
    public String encode(String src,String key) {
        try {
            //得到一个指定的编码格式的字节数组，Linux和windows默认的编码格式不同，所以要指定特定的编码
            byte[] data = src.getBytes(charset);
            byte[] keys = key.getBytes();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < data.length; i++) {
                //结合key和相应的数据进行加密操作,0xff的作用是补码，byte是8bits，而int是32bits
                int n = (0xff & data[i]) + (0xff & keys[i % keys.length]);
                sb.append("_" + n);
            }
            return sb.toString();
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return src;
    }

    /**
     * 解密算法
     * @param src md5处理后的数据进行签名后加密了的字符串
     * @param key 用于加密的值
     * @return
     */
    public String decode(String src,String key) {
        if(src == null || src.length() == 0){
            return src;
        }
        //正则表达式字符串匹配
        Matcher m = pattern.matcher(src);

        List<Integer> list = new ArrayList<Integer>();
        //find方法(部分匹配):尝试去发现输入串中是否匹配相应的子串
        while (m.find()) {
            try {
                //返回匹配到的子字符串
                String group = m.group();
                list.add(Integer.valueOf(group));
            } catch (Exception e) {
                e.printStackTrace();
                return src;
            }
        }

        //如果有匹配的字符串
        if (list.size() > 0) {
            try {
                byte[] data = new byte[list.size()];
                byte[] keys = key.getBytes();
                //相对于加密过程的解密过程
                for (int i = 0; i < data.length; i++) {
                    data[i] = (byte) (list.get(i) - (0xff & keys[i % keys.length]));
                }
                return new String(data, charset);
            } catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }
            return src;
        } else {
            return src;
        }
    }

    /**
     * md5加密
     * @param signStr
     * @return
     */
    public String sign(String signStr){
        //DigestUtils.md5Hex()方法Java调用Apache commons codec实现md5加密，计算MD5摘要并返回值为32个字符的十六进制字符串
        return shuffleSign(DigestUtils.md5Hex(signStr));
    }
    private static byte[][] shufflePos=new byte[][]{{1,13},{5,17},{7,23}};

    private String shuffleSign(String src){
        if(src == null || src.length() == 0){
            return src;
        }
        try {
            //得到一个指定的编码格式的字节数组
            byte[] bytes=src.getBytes(charset);
            byte temp;
            //循环遍历shufflePos，将二维数组中每位一维数组中的每个元素进行换位
            for(int i=0; i<shufflePos.length; i++){
                temp=bytes[shufflePos[i][0]];
                bytes[shufflePos[i][0]]=bytes[shufflePos[i][1]];
                bytes[shufflePos[i][1]]=temp;
            }
            return new String(bytes);
        } catch (UnsupportedEncodingException e) {
            return src;
        }
    }

}
