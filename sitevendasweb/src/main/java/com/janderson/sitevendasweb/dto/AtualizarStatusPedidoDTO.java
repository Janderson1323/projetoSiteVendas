package com.janderson.sitevendasweb.dto;

import com.janderson.sitevendasweb.entity.StatusPedido;

public class AtualizarStatusPedidoDTO {

    private StatusPedido status;

    public AtualizarStatusPedidoDTO() {
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }
}