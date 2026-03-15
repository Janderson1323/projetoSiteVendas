package com.janderson.sitevendasweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.janderson.sitevendasweb.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}