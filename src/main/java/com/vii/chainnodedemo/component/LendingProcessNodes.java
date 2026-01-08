//package com.vii.chainnodedemo.component;
//
//import com.vii.chainnodedemo.chain.ChainNode;
//import com.vii.chainnodedemo.chain.ChainNodeAnno;
//import com.vii.chainnodedemo.chain.ChainTypeEnum;
//import com.vii.chainnodedemo.dto.LendRequest;
//import com.vii.chainnodedemo.dto.LendResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
////@Slf4j
////@Component
//public class LendingProcessNodes {
//
//    private static final Logger log = LoggerFactory.getLogger(LendingProcessNodes.class);
//
//    @ChainNodeAnno(chainType = ChainTypeEnum.LEND_ROUTE, order = 1, requestClass = LendRequest.class, responseClass = LendResponse.class)
//    public LendResponse validateUser(LendRequest request, ChainNode<LendRequest, LendResponse> next) {
//        log.info("1. [Validation] Checking user: {}", request.getUserId());
//        if (request.getAmount() <= 0) return new LendResponse(false, "Invalid loan amount!");
//        return next.execute(request);
//    }
//
//    @ChainNodeAnno(chainType = ChainTypeEnum.LEND_ROUTE, order = 2, requestClass = LendRequest.class, responseClass = LendResponse.class)
//    public LendResponse checkRisk(LendRequest request, ChainNode<LendRequest, LendResponse> next) {
//        log.info("2. [Risk] Calculates the risk for the amount: {}", request.getAmount());
//        if (request.getAmount() > 10000000) return new LendResponse(false, "The risk is too high, the loan is rejected.");
//        return next.execute(request);
//    }
//
//    @ChainNodeAnno(chainType = ChainTypeEnum.LEND_ROUTE, order = 3, requestClass = LendRequest.class, responseClass = LendResponse.class)
//    public LendResponse disbursement(LendRequest request, ChainNode<LendRequest, LendResponse> next) {
//        log.info("3. [Disbursement] Disburses funds to the user: {}", request.getUserId());
//        return new LendResponse(true, "Amount of funds were successfully disbursed = " + request.getAmount());
//    }
//}