package com.janderson.sitevendasweb.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.janderson.sitevendasweb.entity.ItemPedido;
import com.janderson.sitevendasweb.entity.Produto;
import com.janderson.sitevendasweb.service.ProdutoService;
import java.time.LocalDateTime;
import com.janderson.sitevendasweb.entity.Pedido;
import com.janderson.sitevendasweb.service.PedidoService;

@Controller
public class CarrinhoController {
	
	@Autowired
	private PedidoService pedidoService;

    private List<ItemPedido> carrinho = new ArrayList<>();

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/carrinho")
    public String verCarrinho(Model model) {
        double total = 0.0;

        for (ItemPedido item : carrinho) {
            total += item.getPrecoUnitario() * item.getQuantidade();
        }

        model.addAttribute("itens", carrinho);
        model.addAttribute("total", total);
        return "carrinho";
    }
    @GetMapping("/carrinho/adicionar/{id}")
    public String adicionarProduto(@PathVariable Long id) {

        Produto produto = produtoService.buscarProdutoPorId(id);

        if (produto != null) {
            ItemPedido item = new ItemPedido();
            item.setProduto(produto);
            item.setQuantidade(1);
            item.setPrecoUnitario(produto.getPreco());

            carrinho.add(item);
        }

        return "redirect:/carrinho";
    }
    
    @GetMapping("/carrinho/remover/{index}")
    public String removerItem(@PathVariable int index) {

        if (index >= 0 && index < carrinho.size()) {
            carrinho.remove(index);
        }

        return "redirect:/carrinho";
    }
    
    @PostMapping("/carrinho/finalizar")
    public String finalizarPedido() {

        if (carrinho.isEmpty()) {
            return "redirect:/carrinho";
        }

        double total = 0.0;

        for (ItemPedido item : carrinho) {
            total += item.getPrecoUnitario() * item.getQuantidade();
        }

        Pedido pedido = new Pedido();
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus("PENDENTE");
        pedido.setValorTotal(total);

        pedido.setItens(new ArrayList<>(carrinho));

        pedidoService.salvarPedido(pedido);

        carrinho.clear();

        return "redirect:/admin/pedidos";
    }
}