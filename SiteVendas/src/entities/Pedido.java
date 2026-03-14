package entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pedido {

    private Long id;
    private LocalDateTime data;
    private StatusPedido status;
    private List<ItemPedido> itens = new ArrayList<>();
    private Cliente cliente;

    public Pedido(long id, LocalDateTime data, StatusPedido status, Cliente cliente) {
        this.id = id;
        this.data = data;
        this.status = status;
        this.cliente = cliente;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemPedido> getItens() {
        return Collections.unmodifiableList(itens); // Protege a lista externa
    }

    public Double getValorTotal() {
        double total = 0.0;
        for (ItemPedido item : itens) {
            total += item.getSubtotal();
        }
        return total;
    }

    public void adicionarItem(ItemPedido item) {
        if (item.getProduto() == null) {
            throw new IllegalArgumentException("O item não possui produto.");
        }

        // Reduz o estoque do produto
        item.getProduto().reduzirEstoque(item.getQuantidade());
        itens.add(item);
    }
}
