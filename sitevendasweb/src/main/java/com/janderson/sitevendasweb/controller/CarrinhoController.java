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
import com.janderson.sitevendasweb.entity.StatusPedido;
import com.janderson.sitevendasweb.service.PedidoService;
import com.janderson.sitevendasweb.service.ProdutoService;

@Controller
public class CarrinhoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ProdutoService produtoService;

    private List<ItemPedido> carrinho = new ArrayList<>();

    @PostMapping("/carrinho/adicionar/{id}")
    public String adicionarProduto(@PathVariable Long id,
                                   @RequestParam(defaultValue = "1") Integer quantidade) {

        Produto produto = produtoService.buscarProdutoPorId(id);

        if (produto == null) {
            return "redirect:/catalogo";
        }

        if (quantidade == null || quantidade <= 0) {
            return "redirect:/catalogo";
        }

        if (produto.getEstoque() == null || produto.getEstoque() <= 0) {
            return "redirect:/catalogo";
        }

        ItemPedido itemExistente = null;

        for (ItemPedido item : carrinho) {
            if (item.getProduto() != null && item.getProduto().getId().equals(id)) {
                itemExistente = item;
                break;
            }
        }

        if (itemExistente != null) {
            int novaQuantidade = itemExistente.getQuantidade() + quantidade;

            if (novaQuantidade > produto.getEstoque()) {
                novaQuantidade = produto.getEstoque();
            }

            itemExistente.setQuantidade(novaQuantidade);
        } else {
            if (quantidade > produto.getEstoque()) {
                quantidade = produto.getEstoque();
            }

            ItemPedido item = new ItemPedido();
            item.setProduto(produto);
            item.setQuantidade(quantidade);
            item.setPrecoUnitario(produto.getPreco());

            carrinho.add(item);
        }

        return "redirect:/carrinho";
    }

    @GetMapping("/carrinho")
    public String verCarrinho(Model model) {
        double total = carrinho.stream()
                .mapToDouble(item -> item.getPrecoUnitario() * item.getQuantidade())
                .sum();

        model.addAttribute("itens", carrinho);
        model.addAttribute("total", total);
        return "carrinho";
    }

    @GetMapping("/carrinho/remover/{index}")
    public String removerItem(@PathVariable int index) {
        if (index >= 0 && index < carrinho.size()) {
            carrinho.remove(index);
        }
        return "redirect:/carrinho";
    }

    @GetMapping("/checkout")
    public String exibirCheckout(Model model) {
        double total = carrinho.stream()
                .mapToDouble(item -> item.getPrecoUnitario() * item.getQuantidade())
                .sum();

        model.addAttribute("itens", carrinho);
        model.addAttribute("total", total);
        return "checkout";
    }

    @PostMapping("/checkout/finalizar")
    public String finalizarPedido(@RequestParam String nomeCliente,
                                  @RequestParam String telefoneCliente,
                                  @RequestParam String cidade,
                                  @RequestParam String endereco,
                                  @RequestParam(required = false) String observacao,
                                  Model model) {

        if (carrinho.isEmpty()) {
            return "redirect:/carrinho";
        }

        Pedido pedido = new Pedido();
        pedido.setNomeCliente(nomeCliente);
        pedido.setTelefoneCliente(telefoneCliente);
        pedido.setCidade(cidade);
        pedido.setEndereco(endereco);
        pedido.setObservacao(observacao);
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus(StatusPedido.PENDENTE);

        double total = 0.0;
        List<ItemPedido> itensPedido = new ArrayList<>();

        for (ItemPedido itemCarrinho : carrinho) {
            Produto produto = produtoService.buscarProdutoPorId(itemCarrinho.getProduto().getId());

            if (produto == null) {
                continue;
            }

            if (produto.getEstoque() < itemCarrinho.getQuantidade()) {
                return "redirect:/carrinho";
            }

            produto.setEstoque(produto.getEstoque() - itemCarrinho.getQuantidade());
            produtoService.salvarProduto(produto);

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setProduto(produto);
            itemPedido.setQuantidade(itemCarrinho.getQuantidade());
            itemPedido.setPrecoUnitario(itemCarrinho.getPrecoUnitario());

            total += itemPedido.getQuantidade() * itemPedido.getPrecoUnitario();
            itensPedido.add(itemPedido);
        }

        pedido.setValorTotal(total);
        pedido.setItens(itensPedido);

        pedidoService.salvarPedido(pedido);

        StringBuilder mensagem = new StringBuilder();

        mensagem.append("NOVO PEDIDO - GRANITINA\n\n");

        mensagem.append("Cliente: ").append(nomeCliente).append("\n");
        mensagem.append("Telefone: ").append(telefoneCliente).append("\n");
        mensagem.append("Cidade: ").append(cidade).append("\n");
        mensagem.append("Endereco: ").append(endereco).append("\n");

        if (observacao != null && !observacao.isBlank()) {
            mensagem.append("Observacao: ").append(observacao).append("\n");
        }

        mensagem.append("\n");
        mensagem.append("Itens do pedido:\n");

        for (ItemPedido item : itensPedido) {
            double subtotalItem = item.getQuantidade() * item.getPrecoUnitario();

            mensagem.append("\n");
            mensagem.append("- ").append(item.getProduto().getNome()).append("\n");
            mensagem.append("  Qtd: ").append(item.getQuantidade()).append("\n");
            mensagem.append("  Preco unitario: R$ ").append(String.format("%.2f", item.getPrecoUnitario())).append("\n");
            mensagem.append("  Subtotal: R$ ").append(String.format("%.2f", subtotalItem)).append("\n");
        }

        mensagem.append("\n");
        mensagem.append("TOTAL DO PEDIDO: R$ ").append(String.format("%.2f", total)).append("\n");
        mensagem.append("\n");
        mensagem.append("Aguardando confirmacao.");

        String linkWhatsapp = "https://wa.me/5511954307383?text="
                + java.net.URLEncoder.encode(mensagem.toString(), java.nio.charset.StandardCharsets.UTF_8);

        model.addAttribute("pedido", pedido);
        model.addAttribute("linkWhatsapp", linkWhatsapp);

        carrinho.clear();

        return "pedido-confirmado";
        
    }
    
}