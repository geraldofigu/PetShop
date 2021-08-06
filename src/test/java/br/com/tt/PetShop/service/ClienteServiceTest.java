package br.com.tt.PetShop.service;

import br.com.tt.PetShop.dto.ClienteBuscaCpf;
import br.com.tt.PetShop.dto.ClienteConsulta;
import br.com.tt.PetShop.dto.ClienteCriacao;
import br.com.tt.PetShop.model.Cliente;
import br.com.tt.PetShop.repository.ClienteRepository;
import br.com.tt.PetShop.testUtils.ClienteTestFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    ClienteRepository clienteRepository;

    @InjectMocks
    ClienteService underTest;

    @Test
    void deveCriar() {
        ClienteCriacao criacao = new ClienteCriacao("Geraldo", "0121638103");
        Cliente cliente = new Cliente(null, criacao.getNome(), criacao.getCpf(), null);
        Cliente clienteSalvo = new Cliente(2l, criacao.getNome(), criacao.getCpf(), null);
        when(clienteRepository.save(cliente)).thenReturn(clienteSalvo);
        Long teste = underTest.criar(criacao);
        assertEquals(2L, teste);
    }

    @Test
    void deveAtualizarNome() {
        Cliente cliente = new Cliente(12L, "Geraldo", "01216381038", null);
        when(clienteRepository.findById(12L)).thenReturn(Optional.of(cliente));
        underTest.atualizarNome(12L, "Gilberto");
        verify(clienteRepository).findById(12L);
        verify(clienteRepository).save(cliente);
    }

    @Test
    void deveListar() {
        when(clienteRepository.findAll())
                .thenReturn(List.of(ClienteTestFactory.criaMargarete(),
                        ClienteTestFactory.criaGeraldo(),
                        ClienteTestFactory.criaRenata()
                ));
        List<ClienteConsulta> clienteConsultas = underTest.listar();
        assertEquals(3, clienteConsultas.size());
        assertThat(clienteConsultas).extracting("nome").containsExactly("Margarete", "Geraldo", "Renata");
    }

    @Test
    void deveBuscarPorCpf() {
        when(clienteRepository.findByCpf("92316642047")).thenReturn(ClienteTestFactory.criaGeraldo());
        ClienteBuscaCpf cliente = underTest.buscarPorCpf("92316642047");
        assertThat(cliente.getNome() == "Geraldo");
        assertThat(cliente.getAnimais() == List.of("Freud", "Panda"));
    }

    @Test
    void deveApagar() {
        underTest.apagar(any());
        verify(clienteRepository).deleteById(any());
    }

}