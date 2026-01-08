package com.vii.chainnodedemo.component.processor;

import com.vii.chainnodedemo.component.AbstractLendRoutePostProcessor;
import com.vii.chainnodedemo.component.LendRoutePostProcessContext;
import com.vii.chainnodedemo.dto.InvestorRoutingLendRespVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @author luthfi.aryarizki
 * @description
 * @date 2025/01/02 11:00
 */

@Slf4j
@Component
public class InvestorBRouteProcessor extends AbstractLendRoutePostProcessor {

    @Override
    protected boolean support(LendRoutePostProcessContext context) {
        // CHECK WHETHER THE INVESTOR CODE IS B
        String investorCode = context.getInvestorChannelRouteDTO().getInvestorCode();
        return "INVESTOR_B".equals(investorCode);
    }

    @Override
    protected InvestorRoutingLendRespVO doExecute(LendRoutePostProcessContext context) {
        log.info(">>> [STEP 2] Processing Special Logic for INVESTOR B");

        // Contoh Logic B
        InvestorRoutingLendRespVO response = context.getInvestorRoutingLendRespVO();
        response.setStatus("APPROVED_BY_B");
        response.setInvestorCode("INVESTOR_B");

        return response;
    }

    @Override
    public int getOrder() {
        // Must be bigger than A to queue behind A
        // -2 Billion + 20 (Greater than +10)
        return Ordered.HIGHEST_PRECEDENCE + 20;
    }
}