package com.vii.chainnodedemo;

import com.vii.chainnodedemo.chain.ChainTypeEnum;
import com.vii.chainnodedemo.chain.RoutingChain;
import com.vii.chainnodedemo.component.LendRoutePostProcessContext;
import com.vii.chainnodedemo.dto.InvestorChannelRouteDTO;
import com.vii.chainnodedemo.dto.InvestorRoutingLendRespVO;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author luthfi.aryarizki
 * @description
 * @date 2025/01/02 11:00
 */

@Component
public class AppRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(AppRunner.class);

    @Resource
    private RoutingChain routingChain;

    @Override
    public void run(String... args) throws Exception {
        log.info("=== TEST STARTED ===");

        // --- TEST CASE 1: Investor A (Highest Priority) ---
        log.info("\n--- [TEST 1] Testing INVESTOR_A ---");
        testRoute("INVESTOR_A");

        // --- TEST CASE 2: Investor B (Second Priority) ---
        log.info("\n--- [TEST 2] Testing INVESTOR_B ---");
        testRoute("INVESTOR_B");

        // --- TEST CASE 3: Foreign / Unknown Investor (Should go to Default/C) ---
        log.info("\n--- [TEST 3] Testing INVESTOR_XYZ (Unknown) ---");
        testRoute("INVESTOR_XYZ");
    }

    private void testRoute(String investorCode) {
        // 1. Prepare DTO containing Investor Code
        InvestorChannelRouteDTO routeDTO = new InvestorChannelRouteDTO();
        routeDTO.setInvestorCode(investorCode);
        routeDTO.setChannel("APP");

        // 2. Prepare Empty Response (to be populated by processor)
        InvestorRoutingLendRespVO respVO = new InvestorRoutingLendRespVO();

        // 3. Wrap into Context (required by AbstractLendRoutePostProcessor)
        LendRoutePostProcessContext context = LendRoutePostProcessContext.builder()
                .investorChannelRouteDTO(routeDTO)
                .investorRoutingLendRespVO(respVO)
                .build();

        // 4. Execute Chain
        // Note: the result type is InvestorRoutingLendRespVO
        InvestorRoutingLendRespVO result =
                routingChain.execute(context, ChainTypeEnum.LEND_ROUTE);

        log.info("Final Result for {}: Status = {}, Investor = {}",
                investorCode, result.getStatus(), result.getInvestorCode());
    }
}