package org.explore.high.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Slf4j
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {
    /**
     * WebMvc配置
     */
    public WebMvcConfiguration() {
        log.info("开始初始化WebMvc配置...");
    }
}
