/**
 * Package: com.learning.spring
 * Description: 
 */
package com.learning.spring;

import java.lang.annotation.Annotation;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

/**
 * Description:  
 * Date: 2018年2月11日 下午3:50:43
 * @author wufenyun 
 */
public class TargetScanner extends ClassPathBeanDefinitionScanner {
    
    private Class<? extends Annotation> annotationClass;
    
    public TargetScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }
    
    public TargetScanner(BeanDefinitionRegistry registry,Class<? extends Annotation> annotationClass) {
        super(registry);
        this.annotationClass = annotationClass;
    }
    
    public void registerFilters() {
        if(null == annotationClass) {
            throw new NullPointerException();
        }
        addIncludeFilter(new AnnotationTypeFilter(annotationClass));
    }
    
    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        return super.doScan(basePackages);
    }
    
    public Class<? extends Annotation> getAnnotationClass() {
        return annotationClass;
    }

    public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }

}
