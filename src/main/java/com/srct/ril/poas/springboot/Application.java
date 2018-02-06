package com.srct.ril.poas.springboot;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import com.srct.ril.poas.dbconfig.DataSourceConfig;


@ComponentScan(basePackages = "com.srct.ril.poas")
@SpringBootApplication(exclude = {  
        DataSourceAutoConfiguration.class})
@MapperScan("com.srct.ril.poas.dao.mapper")  
public class Application {

   public static void main(String[] args) {
       SpringApplication.run(Application.class, args);
   }

}
