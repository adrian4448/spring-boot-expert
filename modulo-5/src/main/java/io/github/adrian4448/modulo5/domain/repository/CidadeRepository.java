package io.github.adrian4448.modulo5.domain.repository;

import io.github.adrian4448.modulo5.domain.entity.Cidade;
import io.github.adrian4448.modulo5.domain.repository.projections.CidadeProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CidadeRepository extends JpaRepository<Cidade, Long>, JpaSpecificationExecutor<Cidade> {

    // busca pelo nome correto
    List<Cidade> findByNome(String nome);

    // Busca pelo nome começando com aquele pedaço
    List<Cidade> findByNomeStartingWith(String nome);

    // busca pelo nome terminando com aquele pedaco
    List<Cidade> findByNomeEndingWith(String nome);

    // busca pelo nome contendo aquele pedaço
    List<Cidade> findByNomeContaining(String nome);

    // busca pelo nome like
    List<Cidade> findByNomeLike(String nome, Sort sort);

    // busca pelo nome like paginado
    Page<Cidade> findByNomeLikePaginado(String nome, Pageable pageable);

    // busca com like ignorando o case enviado
    @Query(" select c from Cidade c where lower(c.nome) like %lower(:nome)% ")
    List<Cidade> findByNomeLikeNoCase(String nome);

    List<Cidade> findByHabitantes(Long habitantes);

    // cidades com valor menor que o enviado (da de fazer menor e igual)
    List<Cidade> findByHabitantesLessThan(Long habitantes);

    // cidades com valor menor que o enviado e com o nome
    List<Cidade> findByHabitantesLessThanAndNome(Long habitantes, String nome);

    // cidades com valor maior que o enviado (da de fazer maior e igual)
    List<Cidade> findByHabitantesGreaterThan(Long habitantes);

    // query com projections
    @Query(nativeQuery = true, value = "select c.id, c.nome from tb_cidade as c where c.nome = :nome")
    List<CidadeProjection> findCidadeNomeAndId(@Param("nome") String nome);
}
