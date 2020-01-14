package com.app.project.domain.dtos.OrderDtos;

import java.math.BigDecimal;

public class OrderPriceSumDto {

    private BigDecimal bigDecimal;

    public OrderPriceSumDto(BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
    }

    public OrderPriceSumDto() {
    }

    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }

    public void setBigDecimal(BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
    }
}
