package kr.or.ddit.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

import kr.or.ddit.view.FileDownloadView;

// bean파일임을 알림
@Configuration
// <context:component-scan base-package="kr.or.ddit" use-default-filters="false">
//	<context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
// 	<context:include-filter type="annotation" expression="org.springframework.web.bind.annotation.ControllerAdvice"/>
// </context:component-scan>
@ComponentScan(basePackages = "kr.or.ddit", useDefaultFilters = false,
				includeFilters = @Filter(type = FilterType.ANNOTATION,
								classes = {Controller.class, ControllerAdvice.class}))
// <context:annotation-config/>는 자동으로 해주고, java에서 없음
@EnableWebMvc // <mvc:annotation-driven/>과 동일
public class ServletConfig extends WebMvcConfigurerAdapter { // WebMvcConfigurerAdapter ==> <mvc:default-servlet-handler/>

	// <mvc:default-servlet-handler/>
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	// <bean class="org.springframework.web.servlet.view.BeanNameViewResolver">
	//	<property name="order" value="0"/>
	// </bean>
	@Bean
	public ViewResolver BeanNameViewResolver() {
		BeanNameViewResolver viewResolver = new BeanNameViewResolver();
		viewResolver.setOrder(1);
		return viewResolver;
	}
	
	// <bean name="jsonView" class="org.springframework.web.servlet.view.json.MappingJackson2JsonView"/>
	@Bean
	public View jsonView() {
		return new MappingJackson2JsonView();
	}
	
	// <bean name="fileDownloadView" class="kr.or.ddit.view.FileDownloadView"/>
	@Bean
	public View fileDownloadView() {
		return new FileDownloadView();
	}
	
	// <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
	// 	<property name="order" value="1"/> 
	// 	<property name="prefix" value="/WEB-INF/views/"/> 
	// 	<property name="suffix" value=".jsp"/>
	// </bean>
	@Bean
	public ViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setOrder(2);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
	// <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
	//	<property name="maxUploadSize" value="5242880"/> <!-- 25MB -->
	//	<property name="maxUploadSizePerFile" value="26214400"/>	<!-- 5MB -->
	// </bean>
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(26214400);
		multipartResolver.setMaxUploadSizePerFile(5242880);
		return multipartResolver;
	}

	// <bean id="messageSource" class="org.springframework.context.support.ReloadableResourceBundleMessageSource">
	//	<property name="basenames">
	//		<list>
	//			<value>classpath:kr/or/ddit/config/msg/error</value>
	//		</list>
	// 	</property>
	// </bean>
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:kr/or/ddit/config/msg/error",
									"classpath:kr/or/ddit/config/msg/message");
		return messageSource;
	}
	
	@Bean
	public LocaleResolver localeResolver() {
		return new SessionLocaleResolver();
	}
	
	// tiles
	@Bean
	public ViewResolver tilesViewResolver() {
		TilesViewResolver tilesViewResolver = new TilesViewResolver(); // 버전3
		tilesViewResolver.setOrder(0);
		tilesViewResolver.setViewClass(TilesView.class);
		return tilesViewResolver;
	}
	
	// tiles
	@Bean 
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		tilesConfigurer.setDefinitions("classpath:kr/or/ddit/config/tiles/tiles-config.xml");
		return tilesConfigurer;
	}
}
