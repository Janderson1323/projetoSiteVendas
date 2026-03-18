package com.janderson.sitevendasweb.dto;

public class DashboardDTO {

    private long totalPedidos;
    private long pedidosPendentes;
    private long pedidosEnviados;
    private long pedidosCancelados;

    public DashboardDTO() {}

    public DashboardDTO(long totalPedidos, long pedidosPendentes, long pedidosEnviados, long pedidosCancelados) {
        this.totalPedidos = totalPedidos;
        this.pedidosPendentes = pedidosPendentes;
        this.pedidosEnviados = pedidosEnviados;
        this.pedidosCancelados = pedidosCancelados;
    }

    public long getTotalPedidos() {
        return totalPedidos;
    }

    public void setTotalPedidos(long totalPedidos) {
        this.totalPedidos = totalPedidos;
    }

    public long getPedidosPendentes() {
        return pedidosPendentes;
    }

    public void setPedidosPendentes(long pedidosPendentes) {
        this.pedidosPendentes = pedidosPendentes;
    }

    public long getPedidosEnviados() {
        return pedidosEnviados;
    }

    public void setPedidosEnviados(long pedidosEnviados) {
        this.pedidosEnviados = pedidosEnviados;
    }

    public long getPedidosCancelados() {
        return pedidosCancelados;
    }

    public void setPedidosCancelados(long pedidosCancelados) {
        this.pedidosCancelados = pedidosCancelados;
    }
}