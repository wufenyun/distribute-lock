package com.learning.spring;

import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.learning.annotation.service.InterceptorAnnotation;

@SpringBootApplication
public class MyApplication  {

    public static void main(String[] args) {
        SpringApplication re = new SpringApplication(MyApplication.class);
        ConfigurableApplicationContext context = re.run(args);
        TargetScanner scanner = new TargetScanner((BeanDefinitionRegistry) context,InterceptorAnnotation.class);
        scanner.registerFilters();
        
        Set<BeanDefinitionHolder> set = scanner.doScan("com.learning.spring");
        AnnotaionService service = context.getBean(AnnotaionService.class);
        service.print();
        System.out.println(set.size());
    }
    
}
