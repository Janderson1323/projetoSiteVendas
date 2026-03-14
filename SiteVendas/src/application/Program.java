package application;

import entities.Carrinho;
import entities.Cliente;
import entities.ItemPedido;
import entities.Pedido;
import entities.Produto;
import services.CarrinhoService;
import services.PedidoService;
import services.ProdutoService;

public class Program {

    public static void main(String[] args) {

        ProdutoService produtoService = new ProdutoService();
        CarrinhoService carrinhoService = new CarrinhoService(produtoService);
        PedidoService pedidoService = new PedidoService();

        Produto dolomita = new Produto(1L, "Dolomita malha 10", "Pedra moída", 50.0, 100, true);
        produtoService.cadastrarProduto(dolomita);

        Cliente cliente = new Cliente(1L, "Janderson", "janderson.mcast@gmail.com", "123456789", "Rua Exemplo, 123");

        Carrinho carrinho = carrinhoService.criarCarrinho();

        carrinhoService.adicionarProduto(carrinho, 1L, 2);

        System.out.println("Itens do carrinho:");
        for (ItemPedido item : carrinho.getItens()) {
            System.out.println(
                    item.getProduto().getNome()
                    + " - Qtd: " + item.getQuantidade()
                    + " - Subtotal: " + item.getSubtotal()
            );
        }

        System.out.println("Total do carrinho: " + carrinhoService.calcularTotal(carrinho));

        Pedido pedido = pedidoService.finalizarPedido(cliente, carrinho);

        System.out.println("Pedido finalizado!");
        System.out.println("ID do pedido: " + pedido.getId());
        System.out.println("Total do pedido: " + pedido.getValorTotal());
        System.out.println("Status do pedido: " + pedido.getStatus());
    }
}