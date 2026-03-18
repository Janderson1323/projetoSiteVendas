package com.janderson.sitevendasweb.mapper;

import com.janderson.sitevendasweb.dto.ProdutoDTO;
import com.janderson.sitevendasweb.dto.ProdutoRequestDTO;
import com.janderson.sitevendasweb.entity.Produto;

public class ProdutoMapper {

    public static ProdutoDTO toDTO(Produto produto) {

        ProdutoDTO dto = new ProdutoDTO();

        dto.setId(produto.getId());
        dto.setNome(produto.getNome());
        dto.setDescricao(produto.getDescricao());
        dto.setPreco(produto.getPreco());
        dto.setEstoque(produto.getEstoque());
        dto.setAtivo(produto.getAtivo());

        return dto;
    }

    public static Produto toEntity(ProdutoRequestDTO dto) {

        Produto produto = new Produto();

        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setEstoque(dto.getEstoque());
        produto.setAtivo(dto.getAtivo());
        produto.setImagemUrl(dto.getImagemUrl());

        return produto;
    }
}