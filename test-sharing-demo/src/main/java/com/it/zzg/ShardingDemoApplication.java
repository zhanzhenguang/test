
package com.it.zzg;

import com.didispace.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSwagger2Doc
@MapperScan(basePackages = {"com.it.zzg.mapper"})
@SpringBootApplication
public class ShardingDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShardingDemoApplication.class, args);
	}
}
