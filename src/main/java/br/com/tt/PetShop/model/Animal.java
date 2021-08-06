package br.com.tt.PetShop.model;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "TB_ANIMAL")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String apelido;
    @Column
    private LocalDate nascimento;
    @Column
    private TipoAnimal tipo;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente tutor;

}
