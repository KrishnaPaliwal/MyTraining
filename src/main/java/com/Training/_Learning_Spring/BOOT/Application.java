package com.myTraining._Learning_Spring.BOOT;

@EnableAutoConfiguration // Sprint Boot Auto Configuration
@ComponentScan(basePackages = "")
@EnableSwagger // auto generation of API docs
@EnableRetry
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableConfigurationProperties

/*81.1 Create a deployable war file
The first step in producing a deployable war file is to provide a SpringBootServletInitializer 
subclass and override its configure method. This makes use of Spring Framework’s Servlet 3.0 
support and allows you to configure your application when it’s launched by the servlet container.
Typically, you update your application’s main class to extend SpringBootServletInitializer:*/

public class Application extends SpringBootServletInitializer {

// override its configure method
@Override
protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
 return application.sources(Application.class).properties(getProperties());

}

public static void main(String[] args) {

 SpringApplication.run(Application.class, args);
}

@Bean
public FilterRegistrationBean correlationHeaderFilter() {
 FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
 filterRegBean.setFilter(new CorrelationHeaderFilter());
 filterRegBean.setUrlPatterns(Arrays.asList("/*"));

 return filterRegBean;
}

static Properties getProperties() {
 Properties props = new Properties();
 props.put("spring.config.location", "classpath:/");
 return props;
}

@Bean
public RequestMappingHandlerMapping defaultAnnotationHandlerMapping() {
 RequestMappingHandlerMapping bean = new RequestMappingHandlerMapping();
 bean.setUseSuffixPatternMatch(false);
 return bean;
}

}
