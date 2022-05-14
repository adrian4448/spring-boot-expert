package org.example.rest.controller;

import org.example.domain.entity.Produto;
import org.example.domain.repositorio.ProdutosRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/produtos")
public class ProdutosController {

    private ProdutosRepository repository;

    public ProdutosController(ProdutosRepository repository) {
        this.repository = repository;
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Produto produtoById(@PathVariable("id") Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto save(@RequestBody @Valid Produto produto) {
        return repository.save(produto);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") Integer id, @RequestBody @Valid Produto produto) {
        repository.findById(id)
                .map(produtoExistente -> {
                    produto.setId(produtoExistente.getId());
                    repository.save(produto);
                    return produto;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não existente"));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
        repository.findById(id)
                .map(produto -> {
                    repository.delete(produto);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não existente"));
    }


    // consulta com query-params do Spring Boot
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Produto> find(Produto produto) {
        // esse cara serve para definir as regras de pesquisa, exemplo: caixa baixa, caixa alta, comeco da string, contem string
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase();

        // esse cara aqui vai ser de fato quem vai adotar as regras e quem vai verificar os parametros de pesquisa
        Example example = Example.of(produto, exampleMatcher);

        // aqui eu utilizo o objeto Example, que faz a pesquisa de acordo com os parametros
        return repository.findAll(example);
    }

}
