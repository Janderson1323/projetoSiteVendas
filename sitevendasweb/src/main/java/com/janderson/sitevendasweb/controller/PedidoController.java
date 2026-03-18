package com.janderson.sitevendasweb.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.janderson.sitevendasweb.dto.AtualizarStatusPedidoDTO;
import com.janderson.sitevendasweb.dto.DashboardDTO;
import com.janderson.sitevendasweb.dto.PedidoDTO;
import com.janderson.sitevendasweb.entity.Pedido;
import com.janderson.sitevendasweb.entity.StatusPedido;
import com.janderson.sitevendasweb.mapper.PedidoMapper;
import com.janderson.sitevendasweb.service.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "API para gerenciamento de pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @Operation(summary = "Listar pedidos, com filtro opcional por status")
    @GetMapping
    public List<PedidoDTO> listarPedidos(@RequestParam(required = false) StatusPedido status) {

        List<Pedido> pedidos;

        if (status != null) {
            pedidos = pedidoService.listarPedidosPorStatus(status);
        } else {
            pedidos = pedidoService.listarPedidos();
        }

        return pedidos.stream()
                .map(PedidoMapper::toDTO)
                .toList();
    }

    @Operation(summary = "Buscar pedido por ID")
    @GetMapping("/{id}")
    public PedidoDTO buscarPedidoPorId(@PathVariable Long id) {
    	Pedido pedido = pedidoService.buscarPorId(id);
        return PedidoMapper.toDTO(pedido);
    }

    @Operation(summary = "Atualizar status do pedido")
    @PatchMapping("/{id}/status")
    public PedidoDTO atualizarStatus(
            @PathVariable Long id,
            @RequestBody AtualizarStatusPedidoDTO dto) {

        Pedido pedido = pedidoService.atualizarStatus(id, dto.getStatus());

        return PedidoMapper.toDTO(pedido);
    }

    @Operation(summary = "Dashboard de pedidos")
    @GetMapping("/dashboard")
    public DashboardDTO dashboard() {
        return pedidoService.obterDashboard();
    }
}