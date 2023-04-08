package com.chang.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @title: SpuBoundTo
 * @Author Chang
 * @Date: 2023/4/5 21:41
 * @Version 1.0
 */
@Data
public class SpuBoundTo {
    private Long spuId;

    private BigDecimal buyBounds;

    private BigDecimal growBounds;
}
