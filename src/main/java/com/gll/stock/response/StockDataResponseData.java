package com.gll.stock.response;

import com.gll.stock.model.StockData;
import com.gll.stock.model.StockDataWrapper;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author gll
 * @datetime 2020-08-23
 **/
@Data
public class StockDataResponseData extends StockDataWrapper {
    private List<List> netWorthData;

    public void transferData(){
        ArrayList<StockData> history = new ArrayList<>();
        List<List> netWorthData = this.getNetWorthData();
        for (List netWorthDatum : netWorthData) {
            String date = (String) netWorthDatum.get(0);
            BigDecimal value= (BigDecimal) netWorthDatum.get(1);
            StockData stockData = new StockData();
            stockData.setDate(date);
            stockData.setValue(value);
            history.add(stockData);
        }
        super.setHistory(history);
        super.setDataMap(toMap(this.getHistory()));
    }

    Map<String,StockData> toMap(List<StockData> stocks){
        Map<String, StockData> dateMap = stocks.stream().collect(Collectors.toMap(e -> e.getDate(), Function.identity()));
        return dateMap;
    }
}
