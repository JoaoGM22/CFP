package com.joaodev.cfp.DTO;

import java.math.BigDecimal;
import java.util.List;

public class ProjecaoDetalhadaDTO {
    private String data;
    private BigDecimal saldo;
    private List<TransacoesDTO> transacoes;

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

    public List<TransacoesDTO> getTransacoes() {
        return transacoes;
    }

    public void setTransacoes(List<TransacoesDTO> transacoes) {
        this.transacoes = transacoes;
    }
}
