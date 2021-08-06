package br.com.tt.PetShop.controller;

import br.com.tt.PetShop.dto.AnimalConsulta;
import br.com.tt.PetShop.dto.AnimalCriacao;
import br.com.tt.PetShop.mapper.AnimalMapper;
import br.com.tt.PetShop.service.AnimalService;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/animais")
@Api(tags = "Animais", description = "Controller para gestão de Animais")
public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    //criar
    @PostMapping
    public ResponseEntity criar(@RequestBody AnimalCriacao criacao) {
        Long id = animalService.criar(criacao);
        URI location = URI.create(String.format("/animais/%s", id));
        return ResponseEntity.created(location).build();
    }

    //atualizarApelido
    @PatchMapping("/{id}")
    public void atualizarApelido(
            @PathVariable("id") Long id, @RequestBody String apelido) {
        animalService.atualizarApelido(id, apelido);
    }

    //listar
    @GetMapping
    public List<AnimalConsulta> listar(){
        return animalService.listar();
    }

    //buscarPorTutor
    @GetMapping("/{cpf}")
    public List<AnimalConsulta> buscaPorTutor(@PathVariable("cpf") String cpf) {
        return animalService.buscaPorTutor(cpf);
    }
    //apagar
    @DeleteMapping("/{id}")
    public void apagar(@PathVariable("id") Long id) {
        animalService.apagar(id);
    }

}
