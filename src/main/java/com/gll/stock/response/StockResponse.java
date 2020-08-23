package com.gll.stock.response;

import lombok.Data;

/**
 * @author gll
 * @datetime 2020-08-22
 **/
@Data
public class StockResponse {

    private Integer code;

    private String message;

    private StockDataResponseData data;

}
