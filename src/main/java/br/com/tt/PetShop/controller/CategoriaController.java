package br.com.tt.PetShop.controller;

import br.com.tt.PetShop.dto.AnimalCriacao;
import br.com.tt.PetShop.dto.CategoriaCriacao;
import br.com.tt.PetShop.service.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
