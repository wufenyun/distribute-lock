/**
 * Package: com.wufenyun.app.spring
 * Description: 
 * Date: 2017年5月11日 下午3:56:18
 */

package com.learning.spring;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Title: IocContext 
 * Description:  
 * Date: 2017年5月11日 下午3:56:18
 * @author wufenyun 
 */
public class IocContext {
	
	/*@Test
	public void testBeanFactory(){
		BeanFactory factory = new XmlBeanFactory(new ClassPathResource("spring-service.xml"));
		AnnotaionService service = (AnnotaionService) factory.getBean("annotaionService");
		service.print();
		String[] names = factory.getAliases("annotaionService");
		System.out.println(names.length);
		System.out.println(factory.isSingleton("annotaionService"));
	}*/
	
	@Test
	public void testContext() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		AnnotaionService singletonObject = new AnnotaionService();
		context.getBeanFactory().registerSingleton("service", singletonObject);
		
		AnnotaionService service = (AnnotaionService) context.getBean("annotaionService");
		service.print();
		
		AnnotaionService service2 = (AnnotaionService) context.getBean("service");
		service2.print();
		context.getBean(AnnotaionService.class).print();
	}
}
