package com.janderson.sitevendasweb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.janderson.sitevendasweb.entity.ItemPedido;
import com.janderson.sitevendasweb.entity.Produto;
import com.janderson.sitevendasweb.repository.ItemPedidoRepository;
import com.janderson.sitevendasweb.repository.ProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    public List<Produto> pesquisarTodosProdutos(String nome) {

        if (nome == null || nome.isBlank()) {
            return produtoRepository.findAll();
        }

        nome = nome.trim();

        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Produto salvarProduto(Produto produto) {

        if (produto.getNome() == null || produto.getNome().isBlank()) {
            throw new RuntimeException("Nome do produto é obrigatório");
        }

        if (produto.getPreco() == null || produto.getPreco() < 0) {
            throw new RuntimeException("Preço inválido");
        }

        if (produto.getEstoque() == null || produto.getEstoque() < 0) {
            throw new RuntimeException("Estoque inválido");
        }

        return produtoRepository.save(produto);
    }

    public Produto buscarProdutoPorId(Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        return produto.orElse(null);
    }

    @Transactional
    public void deletarProduto(Long id) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        List<ItemPedido> itens = itemPedidoRepository.findByProduto_Id(id);

        if (!itens.isEmpty()) {

            ItemPedido item = itens.get(0);

            throw new RuntimeException(
                "Não é possível excluir o produto '" 
                + produto.getNome() 
                + "'. Ele está relacionado ao pedido #" 
                + item.getPedido().getId()
            );
        }

        produtoRepository.delete(produto);
    }

    

    public Produto atualizarProduto(Long id, Produto produtoAtualizado) {

        Optional<Produto> produtoExistente = produtoRepository.findById(id);

        if (produtoExistente.isPresent()) {

            Produto produto = produtoExistente.get();

            produto.setNome(produtoAtualizado.getNome());
            produto.setDescricao(produtoAtualizado.getDescricao());
            produto.setPreco(produtoAtualizado.getPreco());
            produto.setEstoque(produtoAtualizado.getEstoque());
            produto.setAtivo(produtoAtualizado.getAtivo());

            return produtoRepository.save(produto);
        }

        return null;
    }

    public Page<Produto> listarProdutosPaginados(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return produtoRepository.findAll(pageable);
    }

    public List<Produto> pesquisarProdutos(String nome) {

        if (nome == null || nome.isBlank()) {
            return produtoRepository.findByAtivoTrueAndEstoqueGreaterThan(0);
        }

        nome = nome.trim();

        return produtoRepository
                .findByNomeContainingIgnoreCaseAndAtivoTrueAndEstoqueGreaterThan(nome, 0);
    }
}