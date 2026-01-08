package com.vii.chainnodedemo.chain;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author luthfi.aryarizki
 * @description
 * @date 2025/01/02 11:00
 */

@SuppressWarnings({"rawtypes", "unchecked"})
@Component
public class RoutingChain extends AbstractAnnotationMethodBeanManager<ChainNodeAnno> {

    private final Map<ChainTypeEnum, ChainNode> chainMap = new HashMap<>();
    private Map<ChainTypeEnum, List<ChainNode>> chainListMap;

    public <Request, Response> Response execute(Request request, ChainTypeEnum chainTypeEnum) {
        ChainNode chainNode = chainMap.get(chainTypeEnum);
        if (chainNode == null) return null;
        return (Response) chainNode.execute(request);
    }

    @Override
    protected void doProcess(String beanName, Class<?> targetType, Method method, ChainNodeAnno annotation) {
        ChainNodeMethodAdapter chainNodeMethodAdapter = new ChainNodeMethodAdapter(configurableApplicationContext.getBean(beanName, targetType), method, annotation);
        List<ChainNode> chainNodes = chainListMap.computeIfAbsent(chainNodeMethodAdapter.getChainType(), k -> new ArrayList<>());
        chainNodes.add(chainNodeMethodAdapter);
    }

    @Override
    public void afterSingletonsInstantiated() {
        handleClassNode();
        super.afterSingletonsInstantiated();
        buildChain();
    }

    private void buildChain() {
        for (Map.Entry<ChainTypeEnum, List<ChainNode>> entry : chainListMap.entrySet()) {
            ChainNode firstNode = null;
            List<ChainNode> chainNodeList = entry.getValue().stream()
                    .sorted(Comparator.comparingInt(ChainNode::getOrder)).collect(Collectors.toList());
            ChainNode lastNode = null;
            for (ChainNode chainNode : chainNodeList) {
                if (firstNode == null) firstNode = chainNode;
                if (lastNode != null) lastNode.setNext(chainNode);
                lastNode = chainNode;
            }
            ChainTypeEnum chainType = entry.getKey();
            if(chainType.isNeedSentinelNode()){
                NoopChainNode noopChainNode = new NoopChainNode(chainType);
                lastNode.setNext(noopChainNode);
            }
            chainMap.put(chainType, firstNode);
        }
    }

    private void handleClassNode() {
        this.chainListMap = SpringUtil.getBeansOfType(ChainNode.class).values().stream()
                .collect(Collectors.groupingBy(ChainNode::getChainType));
    }
}