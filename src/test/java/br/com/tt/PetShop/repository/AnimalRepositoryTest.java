package br.com.tt.PetShop.repository;

import br.com.tt.PetShop.model.Animal;
import br.com.tt.PetShop.model.Cliente;
import br.com.tt.PetShop.model.TipoAnimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
class AnimalRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    AnimalRepository underTest;

    @Test
    void deveBuscarPoCpf() {
        Cliente cliente = new Cliente(null, "Geraldo", "01216381038", null);
        testEntityManager.persist(cliente);
        Animal a1 = new Animal(null, "Freud", LocalDate.of(2014,11,8), TipoAnimal.CAO, cliente);
        testEntityManager.persist(a1);
        Animal a2 = new Animal(null, "Panda", LocalDate.of(2002,4,22), TipoAnimal.CAO, cliente);
        testEntityManager.persist(a2);
        List<Animal> animalList = underTest.findByTutorCpf(cliente.getCpf());
        assertEquals(2, animalList.size());
        assertThat(animalList)
                .extracting("apelido")
                .containsExactly("Freud", "Panda");
        assertThat(animalList)
                .extracting("id").isNotNull();
    }

}