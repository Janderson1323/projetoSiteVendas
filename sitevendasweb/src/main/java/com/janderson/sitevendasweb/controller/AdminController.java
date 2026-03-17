package com.janderson.sitevendasweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.janderson.sitevendasweb.entity.Pedido;
import com.janderson.sitevendasweb.entity.Produto;
import com.janderson.sitevendasweb.service.PedidoService;
import com.janderson.sitevendasweb.service.ProdutoService;

@Controller
public class AdminController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/admin")
    public String adminHome(Model model) {

        List<Produto> produtos = produtoService.listarProdutos();
        List<Pedido> pedidos = pedidoService.listarPedidos();

        long totalProdutos = produtos.size();
        long totalPedidos = pedidos.size();

        long pedidosPendentes = pedidos.stream()
                .filter(p -> "PENDENTE".equalsIgnoreCase(p.getStatus()))
                .count();

        double valorTotalVendido = pedidos.stream()
                .mapToDouble(Pedido::getValorTotal)
                .sum();

        model.addAttribute("totalProdutos", totalProdutos);
        model.addAttribute("totalPedidos", totalPedidos);
        model.addAttribute("pedidosPendentes", pedidosPendentes);
        model.addAttribute("valorTotalVendido", valorTotalVendido);

        return "admin/index";
    }
}