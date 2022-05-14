package org.example.rest.controller;

import org.example.domain.entity.ItemPedido;
import org.example.domain.entity.Pedido;
import org.example.rest.dto.InformacaoPedidoDTO;
import org.example.rest.dto.InformacoesPedidoDTO;
import org.example.rest.dto.PedidoDTO;
import org.example.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/pedidos")
public class PedidosController {

    private PedidoService service;

    public PedidosController(PedidoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody @Valid PedidoDTO dto) {
        Pedido pedido = service.salvar(dto);
        return pedido.getId();
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public InformacoesPedidoDTO getPedidoInfo(@PathVariable("id") Integer id) {
        return service.obterPedidoCompleto(id)
                .map(p -> converter(p))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
    }

    private InformacoesPedidoDTO converter(Pedido pedido) {
        return InformacoesPedidoDTO.builder()
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/mm/yyyy")))
                .codigo(pedido.getId())
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .status(pedido.getStatus().name())
                .items(converter(pedido.getItens()))
                .build();
    }

    private List<InformacaoPedidoDTO> converter(List<ItemPedido> itens) {
        if(CollectionUtils.isEmpty(itens)) {
            return Collections.emptyList();
        }

        return itens.stream().map(item ->
                InformacaoPedidoDTO
                        .builder()
                        .descricaoProduto(item.getProduto().getDescricao())
                        .precoUnitario(item.getProduto().getPrecoUnitario())
                        .quantidade(item.getQuantidade())
                        .build())
                .collect(Collectors.toList());
    }
}
