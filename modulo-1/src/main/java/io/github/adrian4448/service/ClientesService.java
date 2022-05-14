package io.github.adrian4448.service;

import io.github.adrian4448.model.Cliente;
import io.github.adrian4448.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientesService {

    @Autowired
    ClientesRepository repository;

    public void salvarCliente(Cliente cliente) {
        validarCliente(cliente);
    }

    public void validarCliente(Cliente cliente) {
        //aplica valida
    }
}
