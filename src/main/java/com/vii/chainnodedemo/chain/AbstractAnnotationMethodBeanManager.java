package com.vii.chainnodedemo.chain;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.aop.framework.autoproxy.AutoProxyUtils;
import org.springframework.aop.scope.ScopedObject;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author luthfi.aryarizki
 * @description
 * @date 2025/01/02 11:00
 */

public abstract class AbstractAnnotationMethodBeanManager<A extends Annotation> implements SmartInitializingSingleton, ApplicationContextAware {

    private final Set<Class<?>> nonAnnotatedClasses = Collections.newSetFromMap(new ConcurrentHashMap<>(64));
    protected Class<A> annotationClass;
    protected ConfigurableApplicationContext configurableApplicationContext;

    public AbstractAnnotationMethodBeanManager(){
        ResolvableType resolvableType = ResolvableType.forClass(this.getClass()).getSuperType();
        Class<?>[] classes = resolvableType.resolveGenerics();
        annotationClass = (Class<A>) classes[0];
    }

    @Override
    public void afterSingletonsInstantiated() {
        String[] beanNames = SpringUtil.getBeanNamesForType(Object.class);
        for (String beanName : beanNames) {
            if (!ScopedProxyUtils.isScopedTarget(beanName)) {
                Class<?> type = null;
                try {
                    type = AutoProxyUtils.determineTargetClass(configurableApplicationContext.getBeanFactory(), beanName);
                } catch (Throwable ex) {}
                if (type != null) {
                    if (ScopedObject.class.isAssignableFrom(type)) {
                        try {
                            Class<?> targetClass = AutoProxyUtils.determineTargetClass(
                                    configurableApplicationContext.getBeanFactory(), ScopedProxyUtils.getTargetBeanName(beanName));
                            if (targetClass != null) type = targetClass;
                        } catch (Throwable ex) {}
                    }
                    try { processBean(beanName, type); }
                    catch (Throwable ex) {
                        throw new BeanInitializationException("Failed to process annotation on bean '" + beanName + "'", ex);
                    }
                }
            }
        }
    }

    protected void processBean(final String beanName, final Class<?> targetType) {
        if (!this.nonAnnotatedClasses.contains(targetType)) {
            Map<Method, A> annotatedMethods = null;
            try {
                annotatedMethods = MethodIntrospector.selectMethods(targetType,
                        (MethodIntrospector.MetadataLookup<A>) method ->
                                AnnotatedElementUtils.findMergedAnnotation(method, annotationClass));
            } catch (Throwable ex) {}
            if (CollectionUtils.isEmpty(annotatedMethods)) {
                this.nonAnnotatedClasses.add(targetType);
            } else {
                for (Map.Entry<Method, A> entry : annotatedMethods.entrySet()) {
                    doProcess(beanName,targetType,entry.getKey(),entry.getValue());
                }
            }
        }
    }

    protected abstract void doProcess(String beanName,Class<?> targetType,Method method,A annotation);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
    }
}