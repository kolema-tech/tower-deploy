package com.sigma.towerdepoyms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhenpeng
 */
@SpringBootApplication
@MapperScan("com.sigma.towerdepoyms.mapper")
public class TowerDepoyMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TowerDepoyMsApplication.class, args);
	}
}
