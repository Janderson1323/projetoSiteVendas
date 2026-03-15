package com.janderson.sitevendasweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.janderson.sitevendasweb.service.PedidoService;

@Controller
public class PedidoViewController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/admin/pedidos")
    public String listarPedidos(Model model) {
        model.addAttribute("pedidos", pedidoService.listarPedidos());
        return "admin/pedidos";
    }
}