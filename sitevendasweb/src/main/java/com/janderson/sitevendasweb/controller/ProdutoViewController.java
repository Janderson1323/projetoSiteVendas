package com.janderson.sitevendasweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
        return "produtos";
    }
    @GetMapping("/admin/produtos/novo")
    public String novoProduto() {
        return "produto-form";
    }
    
    @PostMapping("/admin/produtos/salvar")
    public String salvarProduto(Produto produto) {
        produtoService.salvarProduto(produto);
        return "redirect:/admin/produtos";
    }


}
