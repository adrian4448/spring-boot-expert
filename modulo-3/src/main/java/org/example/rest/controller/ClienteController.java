package org.example.rest.controller;

import org.example.domain.entity.Cliente;
import org.example.domain.repositorio.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

// forma mais 'antiga' de fazer um controller no Spring

@Controller
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    ClientesRepository repository;

    @GetMapping("{id}")
    @ResponseBody
    public ResponseEntity<Cliente> clienteById(@PathVariable("id") Integer id) {
        Optional<Cliente> cliente = repository.findById(id);
        if(cliente.isPresent()) {
            return ResponseEntity.ok().body(cliente.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Cliente> save(@RequestBody @Valid Cliente cliente) {
        Cliente clienteSaved = repository.save(cliente);

        return ResponseEntity.ok().body(clienteSaved);
    }

    @DeleteMapping("{id}")
    @ResponseBody
    public ResponseEntity delete(@PathVariable("id") Integer id) {
        Optional<Cliente> clienteToDelete = repository.findById(id);

        if(clienteToDelete.isPresent()) {
            repository.delete(clienteToDelete.get());
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("{id}")
    @ResponseBody
    public ResponseEntity update(@PathVariable("id") Integer id, @RequestBody @Valid Cliente cliente) {
        return repository.findById(id)
                .map(clienteExistente -> {
                    cliente.setId(clienteExistente.getId());
                    repository.save(cliente);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // consulta com query-params do Spring Boot
    @GetMapping()
    @ResponseBody
    public ResponseEntity find(Cliente filtro) {
        // esse cara serve para definir as regras de pesquisa, exemplo: caixa baixa, caixa alta, comeco da string, contem string
        ExampleMatcher exampleMatcher = ExampleMatcher
                                            .matching()
                                            .withIgnoreCase()
                                            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        // esse cara aqui vai ser de fato quem vai adotar as regras e quem vai verificar os parametros de pesquisa
        Example example = Example.of(filtro, exampleMatcher);

        // aqui eu utilizo o objeto Example, que faz a pesquisa de acordo com os parametros
        List<Cliente> clientes = repository.findAll(example);

        return ResponseEntity.ok().body(clientes);
    }

}
