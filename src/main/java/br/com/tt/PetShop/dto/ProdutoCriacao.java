package br.com.tt.PetShop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProdutoCriacao {

    private String descricao;
    private BigDecimal valor;
    private Long CategoriaId;

}
