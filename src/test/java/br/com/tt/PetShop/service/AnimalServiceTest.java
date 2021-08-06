package br.com.tt.PetShop.service;

import br.com.tt.PetShop.dto.AnimalConsulta;
import br.com.tt.PetShop.dto.AnimalCriacao;
import br.com.tt.PetShop.mapper.AnimalMapper;
import br.com.tt.PetShop.model.Animal;
import br.com.tt.PetShop.model.Cliente;
import br.com.tt.PetShop.model.TipoAnimal;
import br.com.tt.PetShop.repository.AnimalRepository;
import br.com.tt.PetShop.testUtils.AnimalTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnimalServiceTest {

    @Mock
    AnimalRepository animalRepository;

    @Mock
    ClienteService clienteService;

    @InjectMocks
    AnimalService underTest;

    @Test
    void deveCriar() {
        AnimalCriacao criacao = new AnimalCriacao("Freud", LocalDate.of(2014, 11, 9), TipoAnimal.CAO , 1L);
        Cliente c1 = new Cliente(1L, "nome", "01216381038", null);
        when(clienteService.acharTutorId(1L)).thenReturn(c1);
        Animal animal = new Animal(null, criacao.getApelido(),criacao.getNascimento(), criacao.getTipo(), c1);
        Animal animalSalvo = new Animal(25L, criacao.getApelido(),criacao.getNascimento(), criacao.getTipo(), c1);
        when(animalRepository.save(animal)).thenReturn(animalSalvo);
        Long teste = underTest.criar(criacao);
        assertEquals(25L, teste);
        verify(animalRepository).save(animal);
    }

    @Test
    void deveCriarSemCliente() {
        AnimalCriacao criacao = new AnimalCriacao("Freud", LocalDate.of(2014, 11, 8), TipoAnimal.CAO, null);
        Animal animal = new Animal(null, criacao.getApelido(), criacao.getNascimento(), criacao.getTipo(), null);
        Animal animalSalvo = new Animal(1L, criacao.getApelido(), criacao.getNascimento(), criacao.getTipo(), null);
        when(animalRepository.save(animal)).thenReturn(animalSalvo);
        Long teste = underTest.criar(criacao);
        assertEquals(1L, teste);
        verify(animalRepository).save(animal);
    }

    @Test
    void deveAtualizarApelido() {
        Animal animal = new Animal(1L, "Frida", LocalDate.of(2014, 11, 8), TipoAnimal.GATO, null);
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        underTest.atualizarApelido(1L, "Freud");
        verify(animalRepository).findById(1L);
        verify(animalRepository).save(animal);
    }

    @Test
    void deveAtualizatNascimento() {
        Animal animal = new Animal(1L, "Frida", LocalDate.of(2014, 11, 8), TipoAnimal.GATO, null);
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        underTest.atualizarNascimento(1L, LocalDate.of(2012, 11, 21));
        verify(animalRepository).findById(1L);
        verify(animalRepository).save(animal);
    }

    @Test
    void deveListar() {
        when(animalRepository.findAll())
                .thenReturn(List.of(AnimalTestFactory.criaFreud(),
                        AnimalTestFactory.criaJulie(),
                        AnimalTestFactory.criaPanda()
                ));
        List<AnimalConsulta> animalConsultas = underTest.listar();
        assertThat(animalConsultas)
                .extracting("apelido")
                .containsExactly("Freud", "Julie", "Panda");
        assertThat(animalConsultas)
                .extracting("tutor")
                .containsExactly("Geraldo", "Geraldo", "Geraldo");
        assertEquals(3, animalConsultas.size());
        verify(animalRepository).findAll();
    }

    @Test
    void deveBuscarPorTutor() {
        when(animalRepository.findByTutorCpf("01216381038"))
                .thenReturn(List.of(AnimalTestFactory.criaFreud(),
                        AnimalTestFactory.criaPanda()
                ));
        List<AnimalConsulta> animais = underTest.buscaPorTutor("01216381038");
        assertThat(animais).extracting("apelido")
                .containsExactly("Freud", "Panda");
        verify(animalRepository).findByTutorCpf("01216381038");
    }

    @Test
    void deveApagar() {
        when(animalRepository.findById(12L)).thenReturn(Optional.of(AnimalTestFactory.criaFreud()));
        underTest.apagar(12L);
        verify(animalRepository).deleteById(12L);
    }

}