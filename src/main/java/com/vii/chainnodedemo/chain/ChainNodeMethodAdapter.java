package com.vii.chainnodedemo.chain;

import cn.hutool.core.util.ReflectUtil;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.MethodParameter;
import org.springframework.util.ClassUtils;
import java.lang.reflect.Method;

/**
 * @author luthfi.aryarizki
 * @description
 * @date 2025/01/02 11:00
 */

public class ChainNodeMethodAdapter extends AbstractChainNode<Object, Object> {
    private final Class<?> beanType;
    private final Method method;
    private final Method bridgedMethod;
    private final MethodParameter[] parameters;
    private final Object bean;
    private ChainNodeAnno chainNodeAnno;

    public ChainNodeMethodAdapter(Object bean, Method method, ChainNodeAnno chainNodeAnno) {
        this.bean = bean;
        this.beanType = ClassUtils.getUserClass(bean);
        this.method = method;
        this.bridgedMethod = BridgeMethodResolver.findBridgedMethod(method);
        this.parameters = initMethodParameters(chainNodeAnno);
        this.chainNodeAnno = chainNodeAnno;
    }

    private MethodParameter[] initMethodParameters(ChainNodeAnno chainNodeAnno) {
        int count = this.bridgedMethod.getParameterCount();
        if (count != 2) throw new RuntimeException("chainNodeAnno method must have 2 parameters");

        Class<?>[] parameterTypes = this.bridgedMethod.getParameterTypes();
        if (!parameterTypes[0].equals(chainNodeAnno.requestClass())) {
            throw new RuntimeException("Request class mismatch");
        }
        if (!parameterTypes[1].equals(ChainNode.class)) {
            throw new RuntimeException("Second parameter must be ChainNode");
        }

        Class<?> returnType = bridgedMethod.getReturnType();
        if (!returnType.equals(chainNodeAnno.responseClass())) {
            throw new RuntimeException("Response class mismatch");
        }

        MethodParameter[] result = new MethodParameter[count];
        for (int i = 0; i < count; i++) {
            MethodParameter parameter = new MethodParameter(this.method, i);
            GenericTypeResolver.resolveParameterType(parameter, this.beanType);
            result[i] = parameter;
        }
        return result;
    }

    @Override
    public Object execute(Object request) {
        ChainNode next = getNext();
        Object[] args = new Object[parameters.length];
        args[0] = request;
        args[1] = next;
        ReflectUtil.setAccessible(bridgedMethod);
        return ReflectUtil.invoke(bean, bridgedMethod, args);
    }

    @Override public int getOrder() { return chainNodeAnno.order(); }
    @Override public ChainTypeEnum getChainType() { return chainNodeAnno.chainType(); }
}