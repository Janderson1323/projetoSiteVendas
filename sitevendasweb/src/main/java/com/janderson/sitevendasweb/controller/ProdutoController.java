

package com.janderson.sitevendasweb.controller;

import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.janderson.sitevendasweb.dto.ProdutoDTO;
import com.janderson.sitevendasweb.dto.ProdutoRequestDTO;
import com.janderson.sitevendasweb.entity.Produto;
import com.janderson.sitevendasweb.mapper.ProdutoMapper;
import com.janderson.sitevendasweb.service.ProdutoService;


@Tag(name = "Produtos", description = "API para gerenciamento de produtos")
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // LISTAR PRODUTOS (DTO)
    @GetMapping
    public List<ProdutoDTO> listarProdutos() {

        return produtoService.listarProdutos()
                .stream()
                .map(ProdutoMapper::toDTO)
                .toList();
    }

    // CRIAR PRODUTO
    @PostMapping
    public Produto salvarProduto(@Valid @RequestBody ProdutoRequestDTO dto) {

        Produto produto = ProdutoMapper.toEntity(dto);

        return produtoService.salvarProduto(produto);
    }

    // BUSCAR PRODUTO POR ID
    @GetMapping("/{id}")
    public Produto buscarProdutoPorId(@PathVariable Long id) {

        return produtoService.buscarProdutoPorId(id);
    }

    // ATUALIZAR PRODUTO
    @PutMapping("/{id}")
    public Produto atualizarProduto(
            @PathVariable Long id,
            @RequestBody Produto produtoAtualizado) {

        return produtoService.atualizarProduto(id, produtoAtualizado);
    }

    // DELETAR PRODUTO
    @DeleteMapping("/{id}")
    public void deletarProduto(@PathVariable Long id) {

        produtoService.deletarProduto(id);
    }

    // BUSCAR POR NOME
    @GetMapping("/busca")
    public List<Produto> pesquisarTodosProdutos(@RequestParam String nome) {

        return produtoService.pesquisarTodosProdutos(nome);
    }

    // PAGINAÇÃO
    @GetMapping("/paginado")
    public Page<Produto> listarProdutosPaginados(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return produtoService.listarProdutosPaginados(page, size);
    }
    
    
}