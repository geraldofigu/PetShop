package br.com.tt.PetShop.dto;

import br.com.tt.PetShop.model.Cliente;
import br.com.tt.PetShop.model.TipoAnimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AnimalCriacao {

    private String apelido;
    private LocalDate nascimento;
    private TipoAnimal tipo;
    private Long tutorId;

}
