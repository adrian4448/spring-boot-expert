package org.example.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.validation.NotEmptyList;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {

    @NotEmpty(message = "Campo cliente é obrigatorio")
    private Integer cliente;

    @NotEmpty(message = "Campo total é obrigatorio")
    private BigDecimal total;

    @NotEmptyList(message = "Pedido nao pode ser realiado sem items")
    private List<ItemPedidoDTO> items;

}
