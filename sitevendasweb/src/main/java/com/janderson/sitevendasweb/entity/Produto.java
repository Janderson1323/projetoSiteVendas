package com.janderson.sitevendasweb.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

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

    // Imagem principal do produto
    private String imagemUrl;

    // Imagens adicionais do produto
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
        name = "produto_imagens",
        joinColumns = @JoinColumn(name = "produto_id")
    )
    @Column(name = "imagem_url")
    private List<String> imagens = new ArrayList<>();


    // Construtor vazio exigido pelo JPA
    public Produto() {
    }


    public Produto(
            Long id,
            String nome,
            String descricao,
            Double preco,
            Integer estoque,
            Boolean ativo) {

        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
        this.ativo = ativo;
    }


    // =========================
    // GETTERS
    // =========================

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

    public String getImagemUrl() {
        return imagemUrl;
    }

    public List<String> getImagens() {
        return imagens;
    }


    // =========================
    // SETTERS
    // =========================

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

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public void setImagens(List<String> imagens) {

        this.imagens = new ArrayList<>();

        if (imagens != null) {

            for (String imagem : imagens) {

                if (imagem != null && !imagem.isBlank()) {
                    this.imagens.add(imagem);
                }

            }
        }
    }

}