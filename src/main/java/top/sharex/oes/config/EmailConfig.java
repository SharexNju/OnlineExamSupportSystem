package top.sharex.oes.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * @author danielyang
 * @Date 2017/10/30 17:13
 */
@Configuration
@ConfigurationProperties(prefix = "email")
public class EmailConfig {
    private @Getter @Setter
    String senderAddress;
    private @Getter @Setter String username;
    private @Getter @Setter String password;
    private @Getter @Setter String hostAddress;
}
