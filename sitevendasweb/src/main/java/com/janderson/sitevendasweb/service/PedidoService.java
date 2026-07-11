package com.janderson.sitevendasweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.janderson.sitevendasweb.dto.DashboardDTO;
import com.janderson.sitevendasweb.entity.Pedido;
import com.janderson.sitevendasweb.entity.StatusPedido;
import com.janderson.sitevendasweb.repository.PedidoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public List<Pedido> listarPedidosPorStatus(StatusPedido status) {
        return pedidoRepository.findByStatus(status);
    }

    public Pedido salvarPedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public void excluirPedido(Long id) {
        pedidoRepository.deleteById(id);
    }
  

   

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    public Pedido atualizarStatus(Long id, StatusPedido status) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        pedido.setStatus(status);

        return pedidoRepository.save(pedido);
    }

    public DashboardDTO obterDashboard() {
        long total = pedidoRepository.count();

        long pendentes = pedidoRepository.countByStatus(StatusPedido.PENDENTE);
        long enviados = pedidoRepository.countByStatus(StatusPedido.ENVIADO);
        long cancelados = pedidoRepository.countByStatus(StatusPedido.CANCELADO);

        return new DashboardDTO(total, pendentes, enviados, cancelados);
    }
}