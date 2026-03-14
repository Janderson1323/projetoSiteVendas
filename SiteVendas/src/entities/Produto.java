package entities;

import java.util.Objects;

public class Produto {

    private Long id;
    private String nome;
    private String descricao;
    private Double preco;
    private Integer estoque;
    private Boolean ativo;

    public Produto() { }

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

    public boolean estaAtivo() {
        return Boolean.TRUE.equals(ativo);
    }

    public boolean temEstoque(int quantidade) {
        return quantidade > 0 && estoque != null && estoque >= quantidade;
    }

    public void reduzirEstoque(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade inválida.");
        }

        if (estoque == null || estoque < quantidade) {
            throw new IllegalArgumentException("Estoque insuficiente para o produto: " + nome);
        }

        estoque -= quantidade;
    }
    
    public void adicionarEstoque(int quantidade) {

        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade inválida.");
        }

        if (estoque == null) {
            estoque = 0;
        }

        estoque += quantidade;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Produto)) return false;
        Produto other = (Produto) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}