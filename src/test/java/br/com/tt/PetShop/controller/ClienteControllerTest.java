package br.com.tt.PetShop.controller;

import br.com.tt.PetShop.dto.ClienteBuscaCpf;
import br.com.tt.PetShop.dto.ClienteConsulta;
import br.com.tt.PetShop.dto.ClienteCriacao;
import br.com.tt.PetShop.service.ClienteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@WebMvcTest(controllers = ClienteController.class)
class ClienteControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ClienteService clienteService;

    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mockMvc);
    }

    @Test
    void deveCriar() throws Exception {

        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(new ClienteCriacao("Geraldo", "723.438.660-23"));

        Mockito.when(clienteService.criar(any(ClienteCriacao.class))).thenReturn(1L);

        mockMvc.perform(MockMvcRequestBuilders.post("/clientes").
                content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isCreated());
        verify(clienteService).criar(any(ClienteCriacao.class));
    }

    @Test
    void deveListar() throws Exception {
        Mockito.when(clienteService.listar()).thenReturn(List.of(
                new ClienteConsulta("Geraldo", "535.182.400-05"),
                new ClienteConsulta("Renata", "723.438.660-23")));

        mockMvc.perform(MockMvcRequestBuilders.get("/clientes"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("[0].nome",
                        CoreMatchers.is("Geraldo")))
                .andExpect(MockMvcResultMatchers.jsonPath("[1].cpf",
                        CoreMatchers.is("723.438.660-23")));
        verify(clienteService).listar();
    }

    @Test
    void deveAtualizarNome() throws Exception {
        Mockito.when(clienteService.listar())
                .thenReturn(List.of(new ClienteConsulta("Geraldo", "723.438.660-23")));

        mockMvc.perform(MockMvcRequestBuilders.patch("/clientes/{id}", 1L).
                content("Renata")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
        verify(clienteService).atualizarNome(1l, "Renata");
    }

    @Test
    void deveBuscarPorCpf() throws Exception {
        ClienteBuscaCpf clienteBuscaCpf = new ClienteBuscaCpf("Geraldo", List.of("Freud", "Panda"));
        Mockito.when(clienteService.buscarPorCpf("979.801.660-27")).thenReturn(clienteBuscaCpf);

        mockMvc.perform(MockMvcRequestBuilders.get("/clientes/{cpf}", "979.801.660-27"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(clienteService).buscarPorCpf("979.801.660-27");
    }

    @Test
    void deveApagar() throws Exception {
        Mockito.when(clienteService.criar(any())).thenReturn(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/clientes/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(clienteService).apagar(1L);
    }

}