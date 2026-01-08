package com.vii.chainnodedemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luthfi.aryarizki
 * @description
 * @date 2025/01/02 11:00
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LendRequest {
    private String userId;
    private Double amount;
}
