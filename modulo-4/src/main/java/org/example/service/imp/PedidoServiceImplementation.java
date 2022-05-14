package org.example.service.imp;

import lombok.RequiredArgsConstructor;
import org.example.domain.entity.Cliente;
import org.example.domain.entity.ItemPedido;
import org.example.domain.entity.Pedido;
import org.example.domain.entity.Produto;
import org.example.domain.enums.StatusPedido;
import org.example.domain.repositorio.ClientesRepository;
import org.example.domain.repositorio.ItemPedidoRepository;
import org.example.domain.repositorio.PedidosRepository;
import org.example.domain.repositorio.ProdutosRepository;
import org.example.exception.BusinessException;
import org.example.rest.dto.ItemPedidoDTO;
import org.example.rest.dto.PedidoDTO;
import org.example.service.PedidoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImplementation implements PedidoService  {

    private final PedidosRepository repository;
    private final ProdutosRepository produtosRepository;
    private final ClientesRepository clientesRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Pedido pedido = new Pedido();
        Cliente cliente = clientesRepository.findById(dto.getCliente())
                .orElseThrow(() -> new BusinessException("Codigo de cliente invalido"));

        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setStatus(StatusPedido.REALIZADO);

        pedido.setCliente(cliente);
        List<ItemPedido> itemsPedidos = converterItems(pedido, dto.getItems());
        repository.save(pedido);
        itemPedidoRepository.saveAll(itemsPedidos);

        pedido.setItens(itemsPedidos);
        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return repository.findByIdFetchItens(id);
    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> dtos) {
        if(dtos.isEmpty()) {
            throw new BusinessException("Pedido nao pode ser concluido");
        }

        return dtos
                .stream()
                .map(dto -> {
                    ItemPedido itemPedido = new ItemPedido();
                    Produto produto = produtosRepository
                            .findById(dto.getProduto())
                            .orElseThrow(() -> new BusinessException("Codigo do produto invalido"));

                    itemPedido.setProduto(produto);
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);

                    return itemPedido;
                }).collect(Collectors.toList());
    }
}
