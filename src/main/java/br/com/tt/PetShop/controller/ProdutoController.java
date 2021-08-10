package br.com.tt.PetShop.controller;

import br.com.tt.PetShop.dto.ProdutoConsulta;
import br.com.tt.PetShop.dto.ProdutoCriacao;
import br.com.tt.PetShop.exception.ProdutoException;
import br.com.tt.PetShop.service.ProdutoService;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

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

    @GetMapping
    public List<ProdutoConsulta> listar(){
        return produtoService.listar();
    }

    @GetMapping("/buscar")
    public List<ProdutoConsulta> buscarPorValor(@RequestBody BigDecimal min, @RequestBody BigDecimal max) {
        return produtoService.buscaPorValor(min, max);
    }

    @DeleteMapping("/{id}")
    public void apagar(@PathVariable("id") Long id) {
        produtoService.apagar(id);
    }

}
