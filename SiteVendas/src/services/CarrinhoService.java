package services;

import entities.Carrinho;
import entities.Produto;

public class CarrinhoService {

    private ProdutoService produtoService;

    public CarrinhoService(ProdutoService produtoService) {
        if (produtoService == null) {
            throw new IllegalArgumentException("ProdutoService inválido");
        }
        this.produtoService = produtoService;
    }

    public Carrinho criarCarrinho() {
        return new Carrinho();
    }

    public void adicionarProduto(Carrinho carrinho, Long idProduto, int quantidade) {
        if (carrinho == null) {
            throw new IllegalArgumentException("Carrinho inválido");
        }

        Produto produto = produtoService.buscarProduto(idProduto);
        carrinho.adicionarItem(produto, quantidade);
    }

    public void removerProduto(Carrinho carrinho, Long idProduto) {
        if (carrinho == null) {
            throw new IllegalArgumentException("Carrinho inválido");
        }

        Produto produto = produtoService.buscarProduto(idProduto);
        carrinho.removerItem(produto);
    }

    public double calcularTotal(Carrinho carrinho) {
        if (carrinho == null) {
            throw new IllegalArgumentException("Carrinho inválido");
        }

        return carrinho.calcularTotal();
    }
}