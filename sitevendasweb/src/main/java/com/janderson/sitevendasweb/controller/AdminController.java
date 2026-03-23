package com.janderson.sitevendasweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.janderson.sitevendasweb.entity.Pedido;
import com.janderson.sitevendasweb.entity.Produto;
import com.janderson.sitevendasweb.entity.StatusPedido;
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
                .filter(p -> p.getStatus() == StatusPedido.PENDENTE)
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

    @GetMapping("/admin/pedidos/{id}")
    public String detalhePedido(@PathVariable Long id, Model model) {
        Pedido pedido = pedidoService.buscarPorId(id);

        model.addAttribute("pedido", pedido);

        return "admin/pedido-detalhe";
    }
}