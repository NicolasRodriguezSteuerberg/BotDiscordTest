package com.example.bot.configuration;

import com.example.bot.utils.constants.CommandConstants;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

public class CustomBeanNameGenerator extends AnnotationBeanNameGenerator {
    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        try {
            String beanClassName = definition.getBeanClassName();
            if (beanClassName != null) {
                Class<?> beanClass = Class.forName(beanClassName);
                if (beanClass.isAnnotationPresent(CustomName.class)) {
                    CustomName customName = beanClass.getAnnotation(CustomName.class);
                    CommandConstants enumValue = customName.value();
                    String beanName = enumValue.getName();
                    return beanName;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return BeanDefinitionReaderUtils.generateBeanName(definition, registry);
    }
}
