package com.test;

import com.gll.stock.execute.StrategyExecutor;
import com.gll.stock.strategy.buy.model.BuyResult;
import com.gll.stock.strategy.buy.StockBuyStrategy;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author gll
 * @datetime 2020-08-22
 **/
public class StockTest {


    private static final String STOCK_CODE = "001180";

    @Test
    public void test() {


        LocalDate startTime = LocalDate.of(2018, 1, 1);

        //从2016年开始购买
        LocalDate endTime = LocalDate.now();
        StockBuyStrategy dayStrategy = StockBuyStrategy.builder()
                .name("每天定投")
                .stockCode(STOCK_CODE)
                .startDate(startTime).endDate(endTime)
                .price(BigDecimal.valueOf(100))
                .firstBuyTimeFunction(e -> e)
                .nextBuyTimeFunction(e -> e.plusDays(1))
                .build();


        StockBuyStrategy monthStrategy = StockBuyStrategy.builder()
                .name("每月1号").stockCode(STOCK_CODE)
                .startDate(startTime).endDate(endTime)
                .price(BigDecimal.valueOf(2000))
                .firstBuyTimeFunction(e -> e.withDayOfMonth(20))
                .nextBuyTimeFunction(e -> {
                    e = e.plusMonths(1);
                    return e.withDayOfMonth(20);
                })
                .build();


        StockBuyStrategy fridayStrategy = StockBuyStrategy.builder()
                .name("每周五").stockCode(STOCK_CODE)
                .startDate(startTime).endDate(endTime)
                .price(BigDecimal.valueOf(500))
                .firstBuyTimeFunction(e -> e.with(ChronoField.DAY_OF_WEEK, 5))
                .nextBuyTimeFunction(e -> {
                    e = e.plusWeeks(1);
                    return e.with(ChronoField.DAY_OF_WEEK, 5);
                })
                .build();


        StockBuyStrategy mondayStrategy = StockBuyStrategy.builder()
                .name("每周一").stockCode(STOCK_CODE)
                .startDate(startTime).endDate(endTime)
                .price(BigDecimal.valueOf(500))
                .firstBuyTimeFunction(e -> e.with(ChronoField.DAY_OF_WEEK, 1))
                .nextBuyTimeFunction(e -> {
                    e = e.plusWeeks(1);
                    return e.with(ChronoField.DAY_OF_WEEK, 1);
                })
                .build();

        StockBuyStrategy tuesDayStrategy = StockBuyStrategy.builder()
                .name("每周二").stockCode(STOCK_CODE)
                .startDate(startTime).endDate(endTime)
                .price(BigDecimal.valueOf(500))
                .firstBuyTimeFunction(e -> e.with(ChronoField.DAY_OF_WEEK, 2))
                .nextBuyTimeFunction(e -> {
                    e = e.plusWeeks(1);
                    return e.with(ChronoField.DAY_OF_WEEK, 2);
                })
                .build();
        StockBuyStrategy wensDayStrategy = StockBuyStrategy.builder()
                .name("每周三").stockCode(STOCK_CODE)
                .startDate(startTime).endDate(endTime)
                .price(BigDecimal.valueOf(500))
                .firstBuyTimeFunction(e -> e.with(ChronoField.DAY_OF_WEEK, 3))
                .nextBuyTimeFunction(e -> {
                    e = e.plusWeeks(1);
                    return e.with(ChronoField.DAY_OF_WEEK, 3);
                })
                .build();

        StockBuyStrategy thursDayStrategy = StockBuyStrategy.builder()
                .name("每周四").stockCode(STOCK_CODE)
                .startDate(startTime).endDate(endTime)
                .price(BigDecimal.valueOf(500))
                .firstBuyTimeFunction(e -> e.with(ChronoField.DAY_OF_WEEK, 4))
                .nextBuyTimeFunction(e -> {
                    e = e.plusWeeks(1);
                    return e.with(ChronoField.DAY_OF_WEEK, 4);
                })
                .build();

        StrategyExecutor executor = new StrategyExecutor();

        List<BuyResult> res = new ArrayList<>();
        res.add(executor.executeStrategy(mondayStrategy));
        res.add(executor.executeStrategy(monthStrategy));
        res.add(executor.executeStrategy(tuesDayStrategy));
        res.add(executor.executeStrategy(wensDayStrategy));
        res.add(executor.executeStrategy(thursDayStrategy));
        res.add(executor.executeStrategy(fridayStrategy));
        res.add(executor.executeStrategy(dayStrategy));

        res.sort(Comparator.comparing(BuyResult::stockYield));
        for (BuyResult re : res) {
            System.out.println(re);
        }

    }



}
