package com.janderson.sitevendasweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.janderson.sitevendasweb.entity.ItemPedido;
import com.janderson.sitevendasweb.repository.ItemPedidoRepository;

@Service
public class ItemPedidoService {

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public List<ItemPedido> listarItens() {
        return itemPedidoRepository.findAll();
    }

    public ItemPedido salvarItem(ItemPedido itemPedido) {
        return itemPedidoRepository.save(itemPedido);
    }

    public ItemPedido buscarItemPorId(Long id) {
        return itemPedidoRepository.findById(id).orElse(null);
    }

    public void deletarItem(Long id) {
        itemPedidoRepository.deleteById(id);
    }
}