package top.sharex.oes.util;

import java.util.Date;

/**
 * @author danielyang
 * @Date 2017/11/21 13:34
 */
public class DateUtil {
    public static Date fromNow(long timeMillis) {
        return new Date(System.currentTimeMillis() + timeMillis);
    }

    public static Date fromNow(int hour, int minutes, int seconds) {
        long millis = ((hour * 60 + minutes) * 60 + seconds) * 1000;
        return fromNow(millis);
    }
}
