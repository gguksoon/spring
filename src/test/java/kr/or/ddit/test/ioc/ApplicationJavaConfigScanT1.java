package kr.or.ddit.test.ioc;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = "kr.or.ddit")
@ImportResource("classpath:kr/or/ddit/config/spring/context-datasource-test.xml")
@EnableWebMvc
public class ApplicationJavaConfigScanT1 {

}
