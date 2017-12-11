package top.sharex.oes;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
@MapperScan("top.sharex.oes.dao")
public class OesApplication {

    public static void main(String[] args) {
        SpringApplication.run(OesApplication.class, args);
    }
}
