package org.example.domain.repositorio;

import org.example.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class ClientesJPA {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public Cliente salvar(Cliente cliente) {
        entityManager.persist(cliente);
        return cliente;
    }

    @Transactional
    public Cliente atualizar(Cliente cliente) {
        entityManager.merge(cliente);
        return cliente;
    }

    @Transactional
    public void deletar(Cliente cliente) {
        Cliente clienteToDelete = entityManager.find(Cliente.class, cliente.getId());

        entityManager.remove(clienteToDelete);
    }

    @Transactional(readOnly = true)
    public List<Cliente> buscarPorNome(String nome) {
        String jpql = "SELECT c FROM Cliente c WHERE c.nome = :nome";
        TypedQuery<Cliente> query = entityManager.createQuery(jpql, Cliente.class)
                .setParameter("nome", nome);
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    public List<Cliente> obterTodos() {
        String jpql = "SELECT c FROM Cliente c";
        TypedQuery<Cliente> query = entityManager.createQuery(jpql, Cliente.class);
        return query.getResultList();
    }

}
