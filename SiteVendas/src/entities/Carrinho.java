package entities;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {

    private List<ItemPedido> itens = new ArrayList<>();
    private static long proximoIdItem = 1;

    
    
    public void adicionarItem(Produto produto, int quantidade) {

        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade inválida.");
        }
        
        if (produto == null) {
            throw new IllegalArgumentException("Produto inválido.");
        }

        // Verifica se o produto já está no carrinho
        ItemPedido existente = null;

        for (ItemPedido item : itens) {
            if (item.getProduto().equals(produto)) {
                existente = item;
                break;
            }
        }

        if (existente != null) {

            int novaQuantidade = existente.getQuantidade() + quantidade;

            if (!produto.temEstoque(novaQuantidade)) {
                throw new IllegalArgumentException("Estoque insuficiente!");
            }

            existente.setQuantidade(novaQuantidade);

        } else {

            if (!produto.temEstoque(quantidade)) {
                throw new IllegalArgumentException("Estoque insuficiente!");
            }

            ItemPedido item = new ItemPedido(proximoIdItem++, produto, quantidade);
            itens.add(item);
        }
    }

    public void removerItem(Produto produto) {
        itens.removeIf(item -> item.getProduto().equals(produto));
    }

    public double calcularTotal() {
        double total = 0;

        for (ItemPedido item : itens) {
            total += item.getSubtotal();
        }

        return total;
    }

    public List<ItemPedido> getItens() {
        return new ArrayList<>(itens);
    }
}