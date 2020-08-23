package com.gll.stock.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author gll
 * @datetime 2020-08-22
 **/
@Data
public class StockData {

    private String date;

    private BigDecimal value;

    private BigDecimal count;
}
