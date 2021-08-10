package br.com.tt.PetShop.service;

import br.com.tt.PetShop.dto.ProdutoConsulta;
import br.com.tt.PetShop.dto.ProdutoCriacao;
import br.com.tt.PetShop.exception.ProdutoException;
import br.com.tt.PetShop.mapper.ProdutoMapper;
import br.com.tt.PetShop.model.Categoria;
import br.com.tt.PetShop.model.Produto;
import br.com.tt.PetShop.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    private final CategoriaService categoriaService;

    public ProdutoService(ProdutoRepository produtoRepository, CategoriaService categoriaService) {
        this.produtoRepository = produtoRepository;
        this.categoriaService = categoriaService;
    }

    //criar
    public Long criar(ProdutoCriacao criacao) {
        Categoria categoria = validarCriacao(criacao);
        // Produto produto = ProdutoMapper.INSTANCE.fromCriar(criacao);
        Produto produto = new Produto(null, criacao.getDescricao(), criacao.getValor(), categoria);
        Produto produtoSalvo = produtoRepository.save(produto);
        return produtoSalvo.getId();

    }

    private Categoria validarCriacao(ProdutoCriacao criacao) {
        Categoria categoria = categoriaService.acharPorId(criacao.getCategoriaId());
        if(categoria.getNome().equalsIgnoreCase("Higiene") &&
                criacao.getValor().floatValue() < 10) {
            throw new ProdutoException("Valor mínimo é R$ 10,00");
        }

        return categoria;

    }

    //atualizarDescricaoOuValor
    public void atualizarDescricaoOuValor(Long id, String descricao, BigDecimal valor) {
        Produto produto = produtoRepository.findById(id).orElseThrow();
        if(descricao != null && valor != null) {
            produto.setDescricao(descricao);
            produto.setValor(valor);
            produtoRepository.save(produto);
        } else if(descricao != null && valor == null) {
            produto.setDescricao(descricao);
            produtoRepository.save(produto);
        } else if(descricao == null && valor != null) {
            produto.setValor(valor);
            produtoRepository.save(produto);
        }
    }
    //listar
    public List<ProdutoConsulta> listar() {
        return produtoRepository.findAll().stream()
                .map(ProdutoMapper.INSTANCE::converterConsulta)
                .collect(Collectors.toList());
    }
    //buscarPorValor
    //TODO montar o teste
    public List<ProdutoConsulta> buscaPorValor(BigDecimal min, BigDecimal max) {
        return produtoRepository.findByValorBetween(min, max).stream()
                .map(ProdutoMapper.INSTANCE::converterConsulta)
                .collect(Collectors.toList());
    }
    //buscarPorCategoria
    //apagar
    public void apagar(Long id) {
        produtoRepository.deleteById(id);
    }

}
