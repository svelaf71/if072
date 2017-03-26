package com.softserve.if072.mvcapp.configuration;


import com.softserve.if072.mvcapp.interceptor.TokenInterceptor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Configuration class for Spring MVC framework
 *
 * @author Igor Parada
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.softserve.if072.mvcapp")
@PropertySource({"classpath:application.properties", "classpath:message.properties"})
public class WebConfig extends WebMvcConfigurerAdapter {

    /**
     * Configures static resources location paths
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("WEB-INF/resources/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("WEB-INF/resources/js/");
        registry.addResourceHandler("/fonts/**").addResourceLocations("WEB-INF/resources/fonts/");
        registry.addResourceHandler("/img/**").addResourceLocations("WEB-INF/resources/img/");
    }

    /**
     * Creates new {@link org.springframework.web.multipart.MultipartResolver} instance
     *
     * @return created instance
     */
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSizePerFile(1048576);  //1MB
        return resolver;
    }

    /**
     * Instantiates new {@link TokenInterceptor} object
     *
     * @return created interceptor instance
     */
    @Bean
    public TokenInterceptor addTokenHeaderInterceptor() {
        return new TokenInterceptor();
    }

    /**
     * Creates RestTemplate Bean with injected {@link TokenInterceptor} instance
     *
     * @return created RestTemplate instance
     */
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate template = new RestTemplate();
        template.setInterceptors(Collections.singletonList(addTokenHeaderInterceptor()));
        return template;
    }

    /**
     * Configure the HttpMessageConverters to use for reading or writing to the body of the request or response.
     *
     * @param converters initially an empty list of converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
        stringConverter.setSupportedMediaTypes(Arrays.asList(new MediaType("text", "plain", Charset.forName("UTF-8"))));
        converters.add(stringConverter);
    }

    /**
     * Creates new {@link org.springframework.context.support.ReloadableResourceBundleMessageSource} instance
     *
     * @return created instance
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("/WEB-INF/resources/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    /**
     * Creates new {@link org.springframework.web.servlet.i18n.CookieLocaleResolver} instance
     *
     * @return created instance
     */
    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setDefaultLocale(new Locale("en"));
        resolver.setCookieName("myLocaleCookie");
        resolver.setCookieMaxAge(4800);
        return resolver;
    }

    /**
     * Creates a validator instance with custom message source
     *
     * @return validator's instance
     */
    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.setValidationMessageSource(messageSource());
        return validatorFactoryBean;
    }

    /**
     * Register our custom validator as a default validator
     */
    @Override
    public Validator getValidator() {
        return validator();
    }

}
