package com.janderson.sitevendasweb.entity;

import jakarta.persistence.*;



@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    private Double preco;

    private Integer estoque;

    private Boolean ativo;
    private String granulometria;
    
    public Produto() {
    }

    public Produto(Long id, String nome, String descricao, Double preco, Integer estoque, Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public Integer getEstoque() {
        return estoque;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setPreco(Double preco) {
        if (preco == null || preco < 0) {
            throw new IllegalArgumentException("Preço inválido.");
        }
        this.preco = preco;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}