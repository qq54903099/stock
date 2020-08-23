package com.gll.stock.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author gll
 * @datetime 2020-08-22
 **/
@Data
public class StockDataWrapper {

    private String code;

    private String name;

    private BigDecimal netWorth;

    private BigDecimal totalWorth;



    private List<StockData> history;

    private Map<String,StockData> dataMap;



}
