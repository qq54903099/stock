package com.gll.stock.strategy.buy;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.function.Function;

/**
 * 基金购买策略
 * @author gll
 * @datetime 2020-08-22
 **/
@Data
@Builder
@Accessors
public class StockBuyStrategy {
    private String name;

    /**
     * 策略开始时间
     */
    private LocalDate startDate;

    /**
     * 策略购买结束时间
     */
    private LocalDate endDate;

    /**
     * 策略卖出时间
     */
    private LocalDate sellDate;

    /**
     * 基金编码
     */
    private String stockCode;

    /**
     * 每次购买多少
     */
    private BigDecimal price;

    /**
     * 第一次购买策略
     */
    private Function<LocalDate,LocalDate> firstBuyTimeFunction;

    /**
     * 下次购买策略
     */
    private Function<LocalDate,LocalDate> nextBuyTimeFunction;

}
