package com.janderson.sitevendasweb.controller;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.janderson.sitevendasweb.entity.ItemPedido;
import com.janderson.sitevendasweb.entity.Pedido;
import com.janderson.sitevendasweb.entity.Produto;
import com.janderson.sitevendasweb.service.PedidoService;
import com.janderson.sitevendasweb.service.ProdutoService;

@Controller
public class CarrinhoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ProdutoService produtoService;

    private List<ItemPedido> carrinho = new ArrayList<>();

    @GetMapping("/carrinho")
    public String verCarrinho(Model model) {
        double total = 0.0;

        StringBuilder mensagem = new StringBuilder();
        mensagem.append("Olá! 👋%0A%0A");
        mensagem.append("Pedido pelo site GRANITINA%0A%0A");

        for (ItemPedido item : carrinho) {
            double totalItem = item.getPrecoUnitario() * item.getQuantidade();
            total += totalItem;

            mensagem.append("Produto: ").append(item.getProduto().getNome()).append("%0A");
            mensagem.append("Quantidade: ").append(item.getQuantidade()).append(" kg%0A");
            mensagem.append("Preço unitário: ").append(item.getPrecoUnitario()).append("%0A");
            mensagem.append("Total item: ").append(totalItem).append("%0A%0A");
        }

        mensagem.append("Total do pedido: ").append(total).append("%0A%0A");
        mensagem.append("Gostaria de combinar retirada ou entrega.");

        model.addAttribute("itens", carrinho);
        model.addAttribute("total", total);
        model.addAttribute("mensagemWhatsapp", mensagem.toString());

        return "carrinho";
    }
    
    @GetMapping("/checkout")
    public String paginaCheckout() {
        return "checkout";
    }

    @PostMapping("/carrinho/adicionar/{id}")
    public String adicionarProduto(@PathVariable Long id,
                                   @RequestParam("quantidade") int quantidade) {

        Produto produto = produtoService.buscarProdutoPorId(id);

        if (produto != null) {
            ItemPedido item = new ItemPedido();
            item.setProduto(produto);
            item.setQuantidade(quantidade);
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

        for (ItemPedido item : carrinho) {
            Produto produto = item.getProduto();

            if (produto != null && produto.getEstoque() != null) {
                int novoEstoque = produto.getEstoque() - item.getQuantidade();

                if (novoEstoque < 0) {
                    novoEstoque = 0;
                }

                produto.setEstoque(novoEstoque);
                produtoService.salvarProduto(produto);
            }
        }

        pedidoService.salvarPedido(pedido);

        carrinho.clear();

        return "redirect:/admin/pedidos";
    }
    
   
    
    @PostMapping("/checkout/finalizar")
    public String finalizarCheckout(
            @RequestParam("nomeCliente") String nomeCliente,
            @RequestParam("telefoneCliente") String telefoneCliente,
            @RequestParam("cidade") String cidade,
            @RequestParam("endereco") String endereco,
            @RequestParam("observacao") String observacao) {

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

        pedido.setNomeCliente(nomeCliente);
        pedido.setTelefoneCliente(telefoneCliente);
        pedido.setCidade(cidade);
        pedido.setEndereco(endereco);
        pedido.setObservacao(observacao);

        for (ItemPedido item : carrinho) {
            Produto produto = item.getProduto();

            if (produto != null && produto.getEstoque() != null) {
                int novoEstoque = produto.getEstoque() - item.getQuantidade();

                if (novoEstoque < 0) {
                    novoEstoque = 0;
                }

                produto.setEstoque(novoEstoque);
                produtoService.salvarProduto(produto);
            }
        }

        pedidoService.salvarPedido(pedido);

        carrinho.clear();

        return "redirect:/admin/pedidos";
    }
}