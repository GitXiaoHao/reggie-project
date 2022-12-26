package com.yh.reggie.config;

import com.yh.reggie.controller.converter.JacksonObjectMapper;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * date 2022/12/25
 *
 * @author yu
 */
@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始设置静态资源映射");
        //访问路径
        registry.addResourceHandler("/backend/**")
                //映射到真实的路径（映射的真实路径末尾必须添加斜杠`/`）
                .addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**")
                .addResourceLocations("classpath:/front/");
        registry.addResourceHandler("/mapper/**")
                .addResourceLocations("classpath:/mapper/");
    }

    /**
     * 扩展mvc框架的消息转换器
     * @param converters 转换器集合
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("扩展消息转换器");
        //创建消息转换器对象
        MappingJackson2HttpMessageConverter
                converter =
                new MappingJackson2HttpMessageConverter();
        //设置对象转换器 使用jackson
        converter.setObjectMapper(new JacksonObjectMapper());
        //将消息转换器追加到mvc框架的转换器集合中
        converters.add(0, converter);
    }
}
