package com.janderson.sitevendasweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.janderson.sitevendasweb.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // ADMIN - busca todos os produtos pelo nome
    List<Produto> findByNomeContainingIgnoreCase(String nome);


    // CLIENTE - lista somente produtos ativos e com estoque
    List<Produto> findByAtivoTrueAndEstoqueGreaterThan(Integer estoque);


    // CLIENTE - busca pelo nome, somente ativos e com estoque
    List<Produto> findByNomeContainingIgnoreCaseAndAtivoTrueAndEstoqueGreaterThan(
            String nome, Integer estoque);

}