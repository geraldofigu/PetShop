package br.com.tt.PetShop.model;

import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "TB_CLIENTE")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long id;
    @Column
    private String nome;
    @Column
    @CPF
    private String cpf;
    @OneToMany(mappedBy = "tutor", cascade = CascadeType.REMOVE)
    private List<Animal> animais;

}
