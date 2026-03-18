package com.janderson.sitevendasweb.dto;

import java.time.LocalDateTime;

import com.janderson.sitevendasweb.entity.StatusPedido;

public class PedidoDTO {

    private Long id;
    private String nomeCliente;
    private String telefoneCliente;
    private String cidade;
    private String endereco;
    private String observacao;
    private StatusPedido status;    private Double valorTotal;
    private LocalDateTime dataPedido;

    public PedidoDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public String getTelefoneCliente() {
        return telefoneCliente;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getObservacao() {
        return observacao;
    }

   
    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public void setTelefoneCliente(String telefoneCliente) {
        this.telefoneCliente = telefoneCliente;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

   

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }
}