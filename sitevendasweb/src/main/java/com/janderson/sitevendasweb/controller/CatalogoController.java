package com.janderson.sitevendasweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.janderson.sitevendasweb.service.ProdutoService;

@Controller
public class CatalogoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/catalogo")
    public String catalogo(
            @RequestParam(required = false) String pesquisa,
            Model model) {

        model.addAttribute("produtos",
                produtoService.pesquisarProdutos(pesquisa));

        model.addAttribute("pesquisa", pesquisa);

        return "catalogo";
    }

}