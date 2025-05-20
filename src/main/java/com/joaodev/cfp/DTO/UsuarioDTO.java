package com.joaodev.cfp.DTO;

import com.joaodev.cfp.entity.Categoria;
import com.joaodev.cfp.entity.Financeiro;
import com.joaodev.cfp.entity.Transacoes;
import java.util.List;

public class UsuarioDTO {

    private Long id;
    private String nome;
    public void setId(Long id) {
        this.id = id;
    }
    private String email;
    private String senha;
    private Financeiro financeiro;
    private List<Transacoes> transacoes;
    private List<Categoria> categorias;
    private String role;

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Financeiro getFinanceiro() {
        return financeiro;
    }

    public void setFinanceiro(Financeiro financeiro) {
        this.financeiro = financeiro;
    }

    public List<Transacoes> getTransacoes() {
        return transacoes;
    }

    public void setTransacoes(List<Transacoes> transacoes) {
        this.transacoes = transacoes;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
