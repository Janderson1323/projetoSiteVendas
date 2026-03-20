package com.janderson.sitevendasweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.janderson.sitevendasweb.entity.Produto;
import com.janderson.sitevendasweb.service.ProdutoService;

@Controller
public class HomeController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/")
    public String home(Model model) {
    	Produto produtoDestaque = produtoService.buscarProdutoPorId(1L);
    	model.addAttribute("produtoDestaque", produtoDestaque);
        return "index";
    }
}