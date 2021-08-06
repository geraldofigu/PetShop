package br.com.tt.PetShop.repository;

import br.com.tt.PetShop.model.Cliente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class ClienteRepositoryTest {

    @Autowired
    ClienteRepository underTest;

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    void deveBuscarPorCpf() {
        Cliente cliente = new Cliente(null, "Geraldo", "01216381038", null);
        testEntityManager.persist(cliente);
        Cliente clienteTeste = underTest.findByCpf(cliente.getCpf());
        assertEquals("Geraldo", clienteTeste.getNome());
        assertNotNull(clienteTeste.getId());

    }
}