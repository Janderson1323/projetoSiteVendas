package com.janderson.sitevendasweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.janderson.sitevendasweb.entity.Pedido;
import com.janderson.sitevendasweb.repository.PedidoRepository;



@Service
public class PedidoService {
	
	
	

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public Pedido salvarPedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public Pedido buscarPedidoPorId(Long id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    public void deletarPedido(Long id) {
        pedidoRepository.deleteById(id);
    }
    
    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id).orElse(null);
    }
    
    public List<Pedido> listarPedidosPorStatus(String status) {
        return pedidoRepository.findByStatusIgnoreCase(status);
    }
    
    public Pedido atualizarStatus(Long id, String status) {

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        pedido.setStatus(status);

        return pedidoRepository.save(pedido);
    }

}