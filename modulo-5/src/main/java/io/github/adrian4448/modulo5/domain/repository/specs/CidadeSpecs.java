package io.github.adrian4448.modulo5.domain.repository.specs;

import io.github.adrian4448.modulo5.domain.entity.Cidade;
import org.springframework.data.jpa.domain.Specification;

public class CidadeSpecs {

    public static Specification<Cidade> nomeEqual(String nome){
        return (root, query, cb) -> cb.equal( root.get("nome"), nome);
    }

    public static Specification<Cidade> habitantesGreaterThan(Integer value){
        return (root, query, cb) -> cb.greaterThan( root.get("habitantes"), value);
    }
}
