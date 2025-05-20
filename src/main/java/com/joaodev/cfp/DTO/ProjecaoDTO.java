package com.joaodev.cfp.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ProjecaoDTO {

    private String data;
    private BigDecimal saldo;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
}
