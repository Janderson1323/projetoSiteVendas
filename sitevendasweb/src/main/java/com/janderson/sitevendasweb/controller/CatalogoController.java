package com.janderson.sitevendasweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.janderson.sitevendasweb.service.ProdutoService;

@Controller
public class CatalogoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/produtos")
    public String catalogo(Model model) {
        model.addAttribute("produtos", produtoService.listarProdutos());
        return "catalogo";
    }
}