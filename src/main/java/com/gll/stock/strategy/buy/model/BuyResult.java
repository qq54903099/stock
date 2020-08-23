package com.gll.stock.strategy.buy.model;

import com.gll.stock.strategy.buy.StockBuyStrategy;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author gll
 * @datetime 2020-08-22
 **/
@Data
public class BuyResult {

    private StockBuyStrategy strategy;

    private List<BuyStockData> buyList;

    private BigDecimal nowPrice;

    private LocalDate lastBuyTime;
    private LocalDate firstBuyTime;
    private LocalDate sellDate;
    private String stockName;
    private String stockCode;

    private BigDecimal allBuyMoney;
    private BigDecimal allBuyCount;




    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("基金:\t\t\t\t").append(this.getStockName()).append("(").append(this.getStockCode()).append(")").append("\n");
        sb.append("策略名称:\t\t\t").append(this.strategy.getName()).append("\n");
        sb.append("开始时间~结束时间:\t").append(this.strategy.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).append("~");
        sb.append(this.strategy.getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).append("\n");
        sb.append("首次购买~末次时间:\t").append(firstBuyTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).append("~");
        sb.append(lastBuyTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).append("\n");
        if(sellDate!=null){
            sb.append("卖出时间:\t\t\t").append(sellDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).append("\n");
        }
        sb.append("购买次数:\t\t\t").append(buyList.size()).append("\n");
        sb.append("购买金额:\t\t\t").append(calculateAllBuyMoney()).append("元").append("\n");
        sb.append("购买份额:\t\t\t").append(calculateAllBuyCount()).append("\n");
        sb.append("共计金额:\t\t\t").append(calculateAllMoney().setScale(2,RoundingMode.HALF_UP)).append("元").append("\n");
        sb.append("盈利金额:\t\t\t").append(profit().setScale(2,RoundingMode.HALF_UP)).append("元").append("\n");

        int year=strategy.getEndDate().getYear() - strategy.getStartDate().getYear();
        BigDecimal diffYear = BigDecimal.valueOf(year<=0?1:year);

        DecimalFormat percentFormat= new DecimalFormat("##.##%");
        sb.append("收益率:\t\t\t\t").append(percentFormat.format(stockYield())).append("\n");
        sb.append("年化收益率:\t\t\t").append(percentFormat.format(stockYield().divide(diffYear,4,RoundingMode.HALF_UP))).append("\n");

        return sb.toString();
    }

    public BigDecimal stockYield(){
        return calculateAllMoney().subtract(calculateAllBuyMoney()).divide(calculateAllBuyMoney(),4, RoundingMode.HALF_UP);
    }

    public BigDecimal profit(){
        return calculateAllMoney().subtract(getAllBuyMoney());
    }

    public BigDecimal calculateAllBuyMoney() {
        if(allBuyMoney!=null){
            return allBuyMoney;
        }
        BigDecimal monthAllMoney = BigDecimal.ZERO;
        for (BuyStockData buyStockData : buyList) {
            monthAllMoney = monthAllMoney.add(buyStockData.getMoney());
        }
        this.allBuyMoney = monthAllMoney;
        return allBuyMoney;
    }
    public BigDecimal calculateAllMoney(){
        return calculateAllBuyCount().multiply(this.getNowPrice());
    }

    public BigDecimal calculateAllBuyCount() {
        if(allBuyCount!=null){
            return allBuyCount;
        }
        BigDecimal monthCount = BigDecimal.ZERO;
        for (BuyStockData buyStockData : buyList) {
            monthCount = monthCount.add(buyStockData.getCount());
        }
        this.allBuyCount = monthCount;
        return allBuyCount;
    }
}
