package com.joaodev.cfp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Financeiro {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private BigDecimal saldo = BigDecimal.valueOf(0);
    private BigDecimal  salario = BigDecimal.valueOf(0);
    @OneToOne
    @JoinColumn(name = "usuario_id", unique = true, nullable = false)
    @JsonBackReference
    private Usuario usuario;
    private BigDecimal rendaMensal;
    private BigDecimal despesaMensal;

    public Financeiro() {}

    public Long getId() {
        return id;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getRendaMensal() {
        return rendaMensal;
    }

    public void setRendaMensal(BigDecimal rendaMensal) {
        this.rendaMensal = rendaMensal;
    }

    public BigDecimal getDespesaMensal() {
        return despesaMensal;
    }

    public void setDespesaMensal(BigDecimal despesaMensal) {
        this.despesaMensal = despesaMensal;
    }

    public Financeiro(BigDecimal saldo, BigDecimal salario, Usuario usuario) {
        this.saldo = saldo;
        this.salario = salario;
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Financeiro{" +
                "id=" + id +
                ", saldo=" + saldo +
                ", salario=" + salario +
                ", rendaMensal=" + rendaMensal +
                ", despesaMensal=" + despesaMensal +
                '}';
    }
}
