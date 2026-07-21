package com.janderson.sitevendasweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.janderson.sitevendasweb.entity.Pedido;
import com.janderson.sitevendasweb.entity.StatusPedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByStatus(StatusPedido status);

    long countByStatus(StatusPedido status);

    List<Pedido> findByItens_Id(Long itemId);
}