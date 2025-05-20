package com.joaodev.cfp.DTO;

import com.joaodev.cfp.entity.Contas;
import com.joaodev.cfp.entity.Transacoes;
import com.joaodev.cfp.entity.Usuario;

import java.math.BigDecimal;
import java.util.List;

public class FinanceiroDTO {

    private BigDecimal saldo;
    private BigDecimal salario;
    private BigDecimal despesaMensal;
    private BigDecimal rendaMensal;
    private List<Transacoes> transacoesRecentes;
    private List<Transacoes> transacoes;
    private List<Contas> contas;
    private Usuario usuario;

    public List<Contas> getContas() {
        return contas;
    }

    public void setContas(List<Contas> contas) {
        this.contas = contas;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public BigDecimal getDespesaMensal() {
        return despesaMensal;
    }

    public void setDespesaMensal(BigDecimal despesaMensal) {
        this.despesaMensal = despesaMensal;
    }

    public BigDecimal getRendaMensal() {
        return rendaMensal;
    }

    public void setRendaMensal(BigDecimal rendaMensal) {
        this.rendaMensal = rendaMensal;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public List<Transacoes> getTransacoesRecentes() {
        return transacoesRecentes;
    }

    public void setTransacoesRecentes(List<Transacoes> transacoesRecentes) {
        this.transacoesRecentes = transacoesRecentes;
    }

    public List<Transacoes> getTransacoes() {
        return transacoes;
    }

    public void setTransacoes(List<Transacoes> transacoes) {
        this.transacoes = transacoes;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
