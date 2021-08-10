package br.com.tt.PetShop.controller;

import br.com.tt.PetShop.dto.AnimalCriacao;
import br.com.tt.PetShop.dto.CategoriaCriacao;
import br.com.tt.PetShop.service.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    //criar
    @PostMapping
    public ResponseEntity criar(@RequestBody CategoriaCriacao criacao) {
        Long id = categoriaService.criar(criacao);
        URI location = URI.create(String.format("/categoria/%s", id));
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable("id") Long id) {
        categoriaService.apagar(id);
    }

}
