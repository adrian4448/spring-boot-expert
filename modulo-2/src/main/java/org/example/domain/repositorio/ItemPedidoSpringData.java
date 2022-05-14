package org.example.domain.repositorio;

import org.example.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoSpringData extends JpaRepository<ItemPedido, Integer> {
}
