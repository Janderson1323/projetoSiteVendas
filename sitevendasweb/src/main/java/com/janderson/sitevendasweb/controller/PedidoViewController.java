package com.janderson.sitevendasweb.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.janderson.sitevendasweb.entity.Pedido;
import com.janderson.sitevendasweb.entity.StatusPedido;
import com.janderson.sitevendasweb.service.PedidoService;

@Controller
@RequestMapping("/admin/pedidos")
public class PedidoViewController {

    private final PedidoService pedidoService;

    public PedidoViewController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public String listarPedidos(
            @RequestParam(required = false) StatusPedido status,
            Model model) {

        List<Pedido> pedidos;

        if (status != null) {
            pedidos = pedidoService.listarPedidosPorStatus(status);
        } else {
            pedidos = pedidoService.listarPedidos();
        }

        model.addAttribute("pedidos", pedidos);
        model.addAttribute("statusSelecionado", status);

        return "admin/pedidos";
    }
}