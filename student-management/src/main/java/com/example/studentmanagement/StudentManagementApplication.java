package com.example.studentmanagement;

import org.apache.catalina.Realm;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.realm.text.PropertiesRealm;
import org.apache.shiro.spring.boot.autoconfigure.ShiroAnnotationProcessorAutoConfiguration;
import org.apache.shiro.spring.boot.autoconfigure.ShiroAutoConfiguration;
import org.apache.shiro.spring.boot.autoconfigure.ShiroBeanAutoConfiguration;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.tomcat.util.http.parser.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.adapter.DefaultAdvisorAdapterRegistry;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@SpringBootApplication(exclude = {ShiroAnnotationProcessorAutoConfiguration.class,
				ShiroAutoConfiguration.class, ShiroBeanAutoConfiguration.class})
public class StudentManagementApplication {

	private static Logger log = LoggerFactory.getLogger(StudentManagementApplication.class);


	@ExceptionHandler(AuthorizationException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN) // return status 403
	public void handlerException(AuthorizationException exception) {
		log.debug("{} was thrown", exception.getClass(), exception);
	}

	@Bean
	public Realm realm() {
		// use "classpath:shiro-users.properties"
		PropertiesRealm realm = new PropertiesRealm();
		return (Realm) realm;
	}

	@Bean
	public ShiroFilterChainDefinition shiroFilterChainDefinition() {
		DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
		chainDefinition.addPathDefinition("**", "authcBasic");
		return chainDefinition;
	}

	@Bean
	public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		defaultAdvisorAutoProxyCreator.setUsePrefix(true);

		return defaultAdvisorAutoProxyCreator;
	}

	public static void main(String[] args) {
		SpringApplication.run(StudentManagementApplication.class, args);
	}

}
