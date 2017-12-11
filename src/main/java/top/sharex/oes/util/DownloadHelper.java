package top.sharex.oes.util;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * @author danielyang
 * @Date 2017/11/1 17:18
 */
@Component
public class DownloadHelper {
    private final static String BASE_DIR = "/download/";

    public void download(HttpServletResponse response,  String fileName) {
        try {
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition",
                    "attachment;fileName=" + fileName);// 设置文件名
            InputStream inputStream = DownloadHelper.class.getResourceAsStream
                    (BASE_DIR + fileName);
            IOUtils.copy(inputStream, response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
