package com.janderson.sitevendasweb.mapper;

import com.janderson.sitevendasweb.dto.PedidoDTO;
import com.janderson.sitevendasweb.entity.Pedido;

public class PedidoMapper {

    public static PedidoDTO toDTO(Pedido pedido) {
        PedidoDTO dto = new PedidoDTO();

        dto.setId(pedido.getId());
        dto.setNomeCliente(pedido.getNomeCliente());
        dto.setTelefoneCliente(pedido.getTelefoneCliente());
        dto.setCidade(pedido.getCidade());
        dto.setEndereco(pedido.getEndereco());
        dto.setObservacao(pedido.getObservacao());
        dto.setStatus(pedido.getStatus());
        dto.setValorTotal(pedido.getValorTotal());
        dto.setDataPedido(pedido.getDataPedido());

        return dto;
    }
}