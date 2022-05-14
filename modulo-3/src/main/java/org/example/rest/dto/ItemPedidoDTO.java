package org.example.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoDTO {

    @NotNull(message = "campo produto é obrigatorio")
    private Integer produto;

    @NotNull(message = "campo quantidade é obrigatorio")
    private Integer quantidade;

}
