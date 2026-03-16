package com.janderson.sitevendasweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.janderson.sitevendasweb.entity.Produto;
import com.janderson.sitevendasweb.service.ProdutoService;

@Controller
public class ProdutoViewController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/admin/produtos")
    public String listarProdutos(Model model) {
        model.addAttribute("produtos", produtoService.listarProdutos());
        return "admin/produtos";
    }

    @GetMapping("/admin/produtos/novo")
    public String novoProduto(Model model) {
        model.addAttribute("produto", new Produto());
        return "admin/produto-form";
    }

    @PostMapping("/admin/produtos/salvar")
    public String salvarProduto(Produto produto) {
        produtoService.salvarProduto(produto);
        return "redirect:/admin/produtos";
    }

    @GetMapping("/admin/produtos/editar/{id}")
    public String editarProduto(@PathVariable Long id, Model model) {
        Produto produto = produtoService.buscarProdutoPorId(id);
        model.addAttribute("produto", produto);
        return "admin/produto-form";
    }

    @PostMapping("/admin/produtos/excluir/{id}")
    public String excluirProduto(@PathVariable Long id) {
        Produto produto = produtoService.buscarProdutoPorId(id);

        if (produto != null) {
            produto.setAtivo(false);
            produtoService.salvarProduto(produto);
        }

        return "redirect:/admin/produtos";
    }

    @GetMapping("/admin/produtos/ativar/{id}")
    public String ativarProduto(@PathVariable Long id) {
        Produto produto = produtoService.buscarProdutoPorId(id);

        if (produto != null) {
            produto.setAtivo(true);
            produtoService.salvarProduto(produto);
        }

        return "redirect:/admin/produtos";
    }

    @GetMapping("/admin/produtos/desativar/{id}")
    public String desativarProduto(@PathVariable Long id) {
        Produto produto = produtoService.buscarProdutoPorId(id);

        if (produto != null) {
            produto.setAtivo(false);
            produtoService.salvarProduto(produto);
        }

        return "redirect:/admin/produtos";
    }
}