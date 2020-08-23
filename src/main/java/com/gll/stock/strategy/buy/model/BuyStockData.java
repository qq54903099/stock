package com.gll.stock.strategy.buy.model;

import com.gll.stock.model.StockData;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author gll
 * @datetime 2020-08-22
 **/
@Data
public class BuyStockData extends StockData {

    private BigDecimal money;

    private LocalDate buyDate;
}
