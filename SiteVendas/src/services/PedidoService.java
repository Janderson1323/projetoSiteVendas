package services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import entities.Carrinho;
import entities.Cliente;
import entities.ItemPedido;
import entities.Pedido;
import entities.StatusPedido;

public class PedidoService {

    private List<Pedido> pedidos = new ArrayList<>();
    private long proximoIdPedido = 1;

    public Pedido finalizarPedido(Cliente cliente, Carrinho carrinho) {

        if (cliente == null) {
            throw new IllegalArgumentException("Cliente inválido");
        }

        if (carrinho == null) {
            throw new IllegalArgumentException("Carrinho inválido");
        }

        if (carrinho.getItens().isEmpty()) {
            throw new IllegalArgumentException("Carrinho vazio");
        }

        Pedido pedido = new Pedido(
                proximoIdPedido++,
                LocalDateTime.now(),
                StatusPedido.AGUARDANDO_PAGAMENTO,
                cliente
        );

        for (ItemPedido item : carrinho.getItens()) {
            pedido.adicionarItem(item);
        }

        pedidos.add(pedido);
        carrinho.limparCarrinho();
        return pedido;
    }

    public Pedido buscarPedido(Long id) {

        if (id == null) {
            throw new IllegalArgumentException("ID inválido");
        }

        for (Pedido pedido : pedidos) {
        	if (pedido.getId() == id) {
                return pedido;
            }
        }

        throw new IllegalArgumentException("Pedido não encontrado");
    }

    public List<Pedido> listarPedidos() {
        return new ArrayList<>(pedidos);
    }
}