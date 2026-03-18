package com.janderson.sitevendasweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.janderson.sitevendasweb.dto.ProdutoDTO;
import com.janderson.sitevendasweb.entity.Produto;
import com.janderson.sitevendasweb.mapper.ProdutoMapper;
import com.janderson.sitevendasweb.service.ProdutoService;
import com.janderson.sitevendasweb.dto.ProdutoRequestDTO;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    
    @GetMapping
    public List<ProdutoDTO> listarProdutos() {

        return produtoService.listarProdutos()
                .stream()
                .map(ProdutoMapper::toDTO)
                .toList();
    }

    @PostMapping
    public Produto salvarProduto(@RequestBody ProdutoRequestDTO dto) {
        Produto produto = ProdutoMapper.toEntity(dto);
        return produtoService.salvarProduto(produto);
    }

    @GetMapping("/{id}")
    public Produto buscarProdutoPorId(@PathVariable Long id) {
        return produtoService.buscarProdutoPorId(id);
    }

    @PutMapping("/{id}")
    public Produto atualizarProduto(@PathVariable Long id, @RequestBody Produto produtoAtualizado) {
        return produtoService.atualizarProduto(id, produtoAtualizado);
    }

    @DeleteMapping("/{id}")
    public void deletarProduto(@PathVariable Long id) {
        produtoService.deletarProduto(id);
    }

    @GetMapping("/busca")
    public List<Produto> buscarPorNome(@RequestParam String nome) {
        return produtoService.buscarPorNome(nome);
    }

    @GetMapping("/paginado")
    public Page<Produto> listarProdutosPaginados(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return produtoService.listarProdutosPaginados(page, size);
    }
}