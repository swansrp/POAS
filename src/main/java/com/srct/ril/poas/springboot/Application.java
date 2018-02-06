package com.srct.ril.poas.springboot;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import com.srct.ril.poas.dbconfig.DataSourceConfig;


@ComponentScan(basePackages = "com.srct.ril.poas")
@SpringBootApplication(exclude = {  
        DataSourceAutoConfiguration.class})
public class Application {

   public static void main(String[] args) {
       SpringApplication.run(Application.class, args);
   }

}
