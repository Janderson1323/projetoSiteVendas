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
        Produto calcario = new Produto(2L, "Calcário filler", "Corretivo agrícola", 80.0, 50, true);
        Produto gesso = new Produto(3L, "Gesso agrícola", "Condicionador de solo", 65.0, 30, true);

        produtoService.cadastrarProduto(dolomita);
        produtoService.cadastrarProduto(calcario);
        produtoService.cadastrarProduto(gesso);

        Cliente cliente = new Cliente(
                1L,
                "Janderson",
                "janderson.mcast@gmail.com",
                "123456789",
                "Rua Exemplo, 123"
        );

        Carrinho carrinho = carrinhoService.criarCarrinho();

        carrinhoService.adicionarProduto(carrinho, 1L, 2);
        carrinhoService.adicionarProduto(carrinho, 2L, 1);
        carrinhoService.adicionarProduto(carrinho, 3L, 3);

        System.out.println("=== ITENS DO CARRINHO ===");
        for (ItemPedido item : carrinho.getItens()) {
            System.out.println(
                    "Produto: " + item.getProduto().getNome()
                    + " | Qtd: " + item.getQuantidade()
                    + " | Preço Unitário: " + item.getPrecoUnitario()
                    + " | Subtotal: " + item.getSubtotal()
            );
        }

        System.out.println();
        System.out.println("Total do carrinho: " + carrinhoService.calcularTotal(carrinho));

        Pedido pedido = pedidoService.finalizarPedido(cliente, carrinho);

        System.out.println();
        System.out.println("=== PEDIDO FINALIZADO ===");
        System.out.println("ID do pedido: " + pedido.getId());
        System.out.println("Cliente: " + pedido.getCliente().getNome());
        System.out.println("Total do pedido: " + pedido.getValorTotal());
        System.out.println("Status do pedido: " + pedido.getStatus());

        System.out.println();
        System.out.println("=== ESTOQUE ATUALIZADO ===");
        for (Produto produto : produtoService.listarProdutos()) {
            System.out.println(
                    produto.getNome() + " - Estoque restante: " + produto.getEstoque()
            );
        }
    }
}