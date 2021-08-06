package br.com.tt.PetShop.controller;

import br.com.tt.PetShop.dto.AnimalConsulta;
import br.com.tt.PetShop.dto.AnimalCriacao;
import br.com.tt.PetShop.model.Animal;
import br.com.tt.PetShop.model.Cliente;
import br.com.tt.PetShop.model.TipoAnimal;
import br.com.tt.PetShop.repository.AnimalRepository;
import br.com.tt.PetShop.service.AnimalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@WebMvcTest(controllers = AnimalController.class)
class AnimalControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AnimalService animalService;

    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mockMvc);
    }

    @Test
    void deveCriar() throws Exception {

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String json = om.writeValueAsString(new AnimalCriacao("Criatura",
                LocalDate.of(2011, 11, 8),
                TipoAnimal.GATO, 1L));

        Mockito.when(animalService.criar(any(AnimalCriacao.class))).thenReturn(1L);

        mockMvc.perform(MockMvcRequestBuilders.post("/animais")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isCreated());
        verify(animalService).criar(any(AnimalCriacao.class));
    }

    @Test
    void deveAtualizarApelido() throws Exception {
        Animal animal = new Animal(12L, "Freud", LocalDate.of(2014,11,8), TipoAnimal.CAO, null);
        Mockito.when(animalService.listar()).thenReturn(List.of(new AnimalConsulta("Freud", null)));
        this.mockMvc.perform(MockMvcRequestBuilders
                        .patch("/animais/{id}", 12)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("Frida"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(animalService).atualizarApelido(12L, "Frida");
    }



    @Test
    void deveListar() throws Exception {
        Cliente cliente = new Cliente(2L, "Geraldo", "12345678909", null);
        Mockito.when(animalService.listar()).thenReturn(List.of(
                new AnimalConsulta("Freud", cliente.getNome()),
                new AnimalConsulta("Frida", cliente.getNome())
        ));
        mockMvc.perform(MockMvcRequestBuilders.get("/animais"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("[0].apelido", CoreMatchers.is("Freud")))
                .andExpect(MockMvcResultMatchers.jsonPath("[1].apelido", CoreMatchers.is("Frida")));
        verify(animalService).listar();
    }

    @Test
    void deveBuscarPorTutor() throws Exception {
        Cliente cliente = new Cliente(2L, "Geraldo", "383.447.460-60", null);
        Animal animal = new Animal(3L, "Freud", LocalDate.of(2004, 11, 8), TipoAnimal.CAO, cliente);
        Mockito.when(animalService.buscaPorTutor("383.447.460-60"))
                .thenReturn(List.of(new AnimalConsulta("Freud", "Geraldo")));

        mockMvc.perform(MockMvcRequestBuilders.get("/animais/{cpf}", "383.447.460-60"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("[0].apelido", CoreMatchers.is("Freud")));
        verify(animalService).buscaPorTutor("383.447.460-60");
    }

    @Test
    void deveApagar() throws Exception {
        Mockito.when(animalService.criar(any())).thenReturn(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/animais/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(animalService).apagar(1L);
    }

}