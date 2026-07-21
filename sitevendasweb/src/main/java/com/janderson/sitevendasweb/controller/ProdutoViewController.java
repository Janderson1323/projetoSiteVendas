package com.janderson.sitevendasweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.janderson.sitevendasweb.entity.ItemPedido;
import com.janderson.sitevendasweb.entity.Produto;
import com.janderson.sitevendasweb.repository.ItemPedidoRepository;
import com.janderson.sitevendasweb.service.ProdutoService;

@Controller
public class ProdutoViewController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;


    @GetMapping("/admin/produtos")
    public String listarProdutos(Model model) {
        model.addAttribute("produtos", produtoService.listarProdutos());
        return "admin/produtos";
    }


    @GetMapping("/admin/produtos/novo")
    public String novoProduto(Model model) {
        model.addAttribute("produto", new Produto());
        return "admin/produto-form";
    }


    @PostMapping("/admin/produtos/salvar")
    public String salvarProduto(Produto produto) {
        produtoService.salvarProduto(produto);
        return "redirect:/admin/produtos";
    }


    @GetMapping("/admin/produtos/editar/{id}")
    public String editarProduto(@PathVariable Long id, Model model) {

        Produto produto = produtoService.buscarProdutoPorId(id);

        model.addAttribute("produto", produto);

        return "admin/produto-form";
    }


    @PostMapping("/admin/produtos/{id}/excluir")
    public String excluirProduto(
            @PathVariable Long id,
            Model model,
            RedirectAttributes redirectAttributes) {

        try {

            produtoService.deletarProduto(id);

            redirectAttributes.addFlashAttribute(
                    "sucesso",
                    "Produto excluído com sucesso!"
            );

            return "redirect:/admin/produtos";


        } catch (RuntimeException e) {
        	
        	System.out.println("Produto ID: " + id);

            System.out.println("ERRO AO EXCLUIR PRODUTO:");
            System.out.println(e.getMessage());

            List<ItemPedido> itens = itemPedidoRepository.findByProduto_Id(id);

            System.out.println("TOTAL DE ITENS ENCONTRADOS: " + itens.size());

            for (ItemPedido item : itens) {

                System.out.println("ITEM ID: " + item.getId());

                if (item.getPedido() != null) {
                    System.out.println("PEDIDO ID: " + item.getPedido().getId());
                } else {
                    System.out.println("PEDIDO: SEM PEDIDO");
                }
            }
            
            

            model.addAttribute("pedidos", itens);

            return "admin/produto-relacionado";
        }
    }
    


    @GetMapping("/admin/produtos/ativar/{id}")
    public String ativarProduto(@PathVariable Long id) {

        Produto produto = produtoService.buscarProdutoPorId(id);

        if (produto != null) {
            produto.setAtivo(true);
            produtoService.salvarProduto(produto);
        }

        return "redirect:/admin/produtos";
    }


    @GetMapping("/admin/produtos/desativar/{id}")
    public String desativarProduto(@PathVariable Long id) {

        Produto produto = produtoService.buscarProdutoPorId(id);

        if (produto != null) {
            produto.setAtivo(false);
            produtoService.salvarProduto(produto);
        }

        return "redirect:/admin/produtos";
    }


    @GetMapping("/produto/{id}")
    public String detalheProduto(
            @PathVariable Long id,
            Model model) {

        Produto produto = produtoService.buscarProdutoPorId(id);

        model.addAttribute("produto", produto);

        return "produto-detalhe";
    }

}