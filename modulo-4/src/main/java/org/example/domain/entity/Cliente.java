package org.example.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Table(name = "cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotEmpty(message = "Campo nome é obrigatorio")
    private String nome;

    @Column(name = "cpf", length = 11)
    @NotEmpty(message = "Campo CPF é obrigatorio")
    @CPF
    private String cpf;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private Set<Pedido> pedidos;

}
