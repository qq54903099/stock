package com.gll.stock.execute;

import com.gll.stock.strategy.buy.model.BuyResult;
import com.gll.stock.strategy.buy.model.BuyStockData;
import com.gll.stock.model.StockData;
import com.gll.stock.model.StockDataWrapper;
import com.gll.stock.strategy.buy.StockBuyStrategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 策略执行器
 * @author gll
 * @datetime 2020-08-23
 **/
public class StrategyExecutor {

    private StockDataWrapper stockWrapper;

    public BuyResult executeStrategy(StockBuyStrategy strategy) {
        StockDataWrapper stockWrapper = getStock(strategy.getStockCode());

        Map<String, StockData> dataMap = stockWrapper.getDataMap();
        BuyResult buyResult = new BuyResult();
        buyResult.setStockName(stockWrapper.getName());
        buyResult.setStockCode(stockWrapper.getCode());
        buyResult.setStrategy(strategy);
        buyResult.setSellDate(strategy.getSellDate());

        LocalDate startTime = strategy.getFirstBuyTimeFunction().apply(strategy.getStartDate());
        LocalDate endTime = strategy.getEndDate();

        BigDecimal money = strategy.getPrice();

        List<BuyStockData> dayBuy = new ArrayList<>();
        do {
            if (startTime.compareTo(endTime) > 0) {
                break;
            }
            BuyStockData dataBuy = buyNext(startTime, endTime, money, dataMap);
            if (dataBuy == null) {
                break;
            }
            buyResult.setLastBuyTime(dataBuy.getBuyDate());
            if (buyResult.getFirstBuyTime() == null) {
                buyResult.setFirstBuyTime(dataBuy.getBuyDate());
            }
            dayBuy.add(dataBuy);
            //获取下次购买日期，不同策略不同
            startTime = strategy.getNextBuyTimeFunction().apply(startTime);
        } while (true);
        buyResult.setBuyList(dayBuy);
        //设置卖出价格,卖出日期

        BuyStockData sellStock = null;
        if(strategy.getSellDate()!=null){
            sellStock=buyNext(strategy.getSellDate(), LocalDate.now(), strategy.getPrice(), dataMap);
        }

        if(sellStock==null){
            buyResult.setNowPrice(stockWrapper.getNetWorth());
        }else{
            buyResult.setNowPrice(sellStock.getValue());
        }
        return buyResult;
    }



    private BuyStockData buyNext(LocalDate startTime, LocalDate endTime, BigDecimal money, Map<String, StockData> dataMap) {
        do {
            if (startTime.compareTo(endTime) > 0) {
                return null;
            }
            String strTime = startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            BuyStockData result = buy(strTime, money, dataMap);
            if (result != null) {
                result.setBuyDate(startTime);
                return result;
            }
            startTime = startTime.plusDays(1);
        } while (true);


    }


    private BuyStockData buy(String date, BigDecimal price, Map<String, StockData> dateMap) {

        StockData stock = dateMap.get(date);
        if (stock == null) {
            //没买到
            return null;
        }
        BuyStockData buyStockData = new BuyStockData();
        buyStockData.setMoney(price);
        buyStockData.setDate(date);
        buyStockData.setValue(stock.getValue());
        buyStockData.setCount(price.divide(stock.getValue(), 4, RoundingMode.HALF_UP));
        return buyStockData;
    }


    private StockDataWrapper getStock(String code) {
        if (stockWrapper != null) {
            return stockWrapper;
        }

        stockWrapper= StockDataCaller.queryStock(code);
        return stockWrapper;
    }
}
