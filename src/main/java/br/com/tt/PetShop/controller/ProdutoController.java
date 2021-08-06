package br.com.tt.PetShop.controller;

import br.com.tt.PetShop.dto.ProdutoCriacao;
import br.com.tt.PetShop.exception.ProdutoException;
import br.com.tt.PetShop.service.ProdutoService;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/produtos")
@Api(tags = "Produtos", description = "Controller para gestão de Produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    //criar
    @PostMapping
    public ResponseEntity criar(@RequestBody ProdutoCriacao criacao) {
        Long id = produtoService.criar(criacao);
        URI location = URI.create(String.format("/produtos/%s", id));
        return ResponseEntity.created(location).build();
    }
}
