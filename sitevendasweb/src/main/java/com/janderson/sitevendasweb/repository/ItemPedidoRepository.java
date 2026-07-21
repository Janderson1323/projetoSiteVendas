package com.janderson.sitevendasweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.janderson.sitevendasweb.entity.ItemPedido;

import jakarta.transaction.Transactional;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
	
	@Modifying
    @Transactional

    void deleteByProduto_Id(Long produtoId);

}