package org.example.domain.repositorio;

import org.example.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutosSpringData extends JpaRepository<Produto, Integer> {
}
