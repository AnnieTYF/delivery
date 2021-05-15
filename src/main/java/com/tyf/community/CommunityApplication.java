package com.tyf.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;

@EnableSwagger2
@SpringBootApplication
public class CommunityApplication {

	@PostConstruct
	public void init(){
		//解决netty启动冲突的问题，在Netty4Utils中,讲key设置为false相关启动冲突的代码就会被屏蔽了
		//Netty4Utils -> setAvailableProcessors
		System.setProperty("es.set.netty.runtime.available.processors", "false");
	}

	public static void main(String[] args) {
		SpringApplication.run(CommunityApplication.class, args);
	}

}
