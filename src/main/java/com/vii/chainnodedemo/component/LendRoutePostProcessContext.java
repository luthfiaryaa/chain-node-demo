package com.vii.chainnodedemo.component;

import com.vii.chainnodedemo.dto.InvestorChannelRouteDTO;
import com.vii.chainnodedemo.dto.InvestorRoutingLendRespVO;
import lombok.Builder;
import lombok.Data;

/**
 * @author luthfi.aryarizki
 * @description
 * @date 2025/01/02 11:00
 */

@Data
@Builder
public class LendRoutePostProcessContext {
    private InvestorChannelRouteDTO investorChannelRouteDTO;
    private InvestorRoutingLendRespVO investorRoutingLendRespVO;
}