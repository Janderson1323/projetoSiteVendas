package com.janderson.sitevendasweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.janderson.sitevendasweb.entity.ItemPedido;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {

    List<ItemPedido> findByProduto_Id(Long produtoId);

}