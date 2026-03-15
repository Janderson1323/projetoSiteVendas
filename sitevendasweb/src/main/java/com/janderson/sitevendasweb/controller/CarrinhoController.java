package com.janderson.sitevendasweb.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.janderson.sitevendasweb.entity.ItemPedido;

@Controller
public class CarrinhoController {

    private List<ItemPedido> carrinho = new ArrayList<>();

    @GetMapping("/carrinho")
    public String verCarrinho(Model model) {
        model.addAttribute("itens", carrinho);
        return "carrinho";
    }
}