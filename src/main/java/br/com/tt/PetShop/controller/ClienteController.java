package br.com.tt.PetShop.controller;

import br.com.tt.PetShop.dto.ClienteBuscaCpf;
import br.com.tt.PetShop.dto.ClienteConsulta;
import br.com.tt.PetShop.dto.ClienteCriacao;
import br.com.tt.PetShop.service.ClienteService;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/clientes")
@Api(tags = "Clientes", description = "Controller para gestão de Clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    //criar
    @PostMapping
    public ResponseEntity criar(@RequestBody ClienteCriacao criacao) {
        Long id = clienteService.criar(criacao);
        URI location = URI.create(String.format("/clientes/%s", id));
        return ResponseEntity.created(location).build();
    }

    //listar
    @GetMapping
    public List<ClienteConsulta> listar() {
        return clienteService.listar();
    }
    //atualizarNome
    @PatchMapping("/{id}")
    public void atualizarNome(@PathVariable("id") Long id, @RequestBody String nome) {
        clienteService.atualizarNome(id, nome);
    }
    //buscarPorCpf
    @GetMapping("/{cpf}")
    public ClienteBuscaCpf buscarPorCpf(@PathVariable("cpf") String cpf) {
        return clienteService.buscarPorCpf(cpf);
    }
    //apagar
    @DeleteMapping("/{id}")
    public void apagar(@PathVariable("id") Long id) {
        clienteService.apagar(id);
    }

}
