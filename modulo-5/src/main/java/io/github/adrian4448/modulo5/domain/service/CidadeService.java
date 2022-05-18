package io.github.adrian4448.modulo5.domain.service;

import io.github.adrian4448.modulo5.domain.entity.Cidade;
import io.github.adrian4448.modulo5.domain.repository.CidadeRepository;
import io.github.adrian4448.modulo5.domain.repository.specs.CidadeSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository repository;

    public void listarCidade() {
        repository.findAll().forEach(System.out::println);
    }

    public void ListarCidadesOrdenado() {
        repository.findByNomeLike("%Porto%", Sort.by("habitantes")).forEach(System.out::println);
    }

    public void ListarCidadesPaginado() {
        Pageable pageable = PageRequest.of(0, 10);
        repository
                .findByNomeLikePaginado("%Porto%", pageable)
                .forEach(System.out::println);
    }

    public List<Cidade> filtroDinamicoComExample(Cidade cidade) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(cidade, exampleMatcher);
        return repository.findAll(example);
    }

    public void listarCidadesByNomeSpec() {
        Specification<Cidade> spec = CidadeSpecs.nomeEqual("São paulo").or(CidadeSpecs.habitantesGreaterThan(1000));
        repository.findAll(spec).forEach(System.out::println);
    }

    public void findCidadeByNomeProjection() {
        repository.findCidadeNomeAndId("Sao paulo")
                .stream()
                .map(cidadeProjection -> new Cidade(cidadeProjection.getId(), cidadeProjection.getNome(), null))
                .forEach(System.out::println);

    }
}
