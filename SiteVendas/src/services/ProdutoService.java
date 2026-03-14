package services;

import java.util.ArrayList;
import java.util.List;

import entities.Produto;

public class ProdutoService {

    private List<Produto> produtos = new ArrayList<>();

    public void cadastrarProduto(Produto produto) {

        if (produto == null) {
            throw new IllegalArgumentException("Produto inválido");
        }

        for (Produto p : produtos) {
            if (p.getId().equals(produto.getId())) {
                throw new IllegalArgumentException("Já existe produto com esse ID");
            }
        }

        produtos.add(produto);
    }

    public Produto buscarProduto(Long id) {

        if (id == null) {
            throw new IllegalArgumentException("ID inválido");
        }

        for (Produto produto : produtos) {
            if (produto.getId().equals(id)) {
                return produto;
            }
        }

        throw new IllegalArgumentException("Produto não encontrado");
    }

    public List<Produto> listarProdutos() {
        return new ArrayList<>(produtos);
    }
    
    
    
}