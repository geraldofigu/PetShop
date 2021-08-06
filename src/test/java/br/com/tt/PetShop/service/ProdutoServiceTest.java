package br.com.tt.PetShop.service;

import br.com.tt.PetShop.dto.ProdutoConsulta;
import br.com.tt.PetShop.dto.ProdutoCriacao;
import br.com.tt.PetShop.exception.ProdutoException;
import br.com.tt.PetShop.mapper.ProdutoMapper;
import br.com.tt.PetShop.model.Categoria;
import br.com.tt.PetShop.model.Produto;
import br.com.tt.PetShop.repository.ProdutoRepository;
import br.com.tt.PetShop.testUtils.ProdutoTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    ProdutoRepository produtoRepository;

    @Mock
    CategoriaService categoriaService;

    @InjectMocks
    ProdutoService underTest;

    @Test
    void deveCriar() {
        ProdutoCriacao criacao = new ProdutoCriacao("Shampoo Anti Pulgas", BigDecimal.valueOf(19.98), 1L);
        Categoria categoria = new Categoria(1L, "Higiene", null);
        when(categoriaService.acharPorId(1L)).thenReturn(categoria);
        Produto produto = new Produto(null, criacao.getDescricao(), criacao.getValor(), categoria);
        Produto produtoSalvo = new Produto(2L, criacao.getDescricao(), criacao.getValor(), categoria);
        when(produtoRepository.save(produto)).thenReturn(produtoSalvo);
        Long teste = underTest.criar(criacao);
        assertEquals(2L, teste);
        verify(produtoRepository).save(produto);
    }

    @Test
    void naoDeveCriar() {
        ProdutoCriacao criacao = new ProdutoCriacao("Shampoo Anti Pulgas", BigDecimal.valueOf(9.98), 1L);
        Categoria categoria = new Categoria(1L, "Higiene", null);
        when(categoriaService.acharPorId(1L)).thenReturn(categoria);
        assertThatThrownBy(() -> underTest.criar(criacao))
                .isInstanceOf(ProdutoException.class)
                .hasMessageContaining("Valor mínimo é R$ 10,00");
    }

    @Test
    void deveAtualizarDescricao() {
        Long id = 3L;
        String descricao = "Shampoo Pelo Preto";
        Produto produto = new Produto(3L, "Shampoo Anti Pulgas", BigDecimal.valueOf(19.98),null);
        when(produtoRepository.findById(3L)).thenReturn(Optional.of(produto));
        underTest.atualizarDescricaoOuValor(3L, descricao, null);
        verify(produtoRepository).save(produto);
        verify(produtoRepository).findById(3L);
    }

    @Test
    void deveAtualizarValor() {
        Long id = 3L;
        BigDecimal valor = BigDecimal.valueOf(35.52);
        Produto produto = new Produto(3L, "Shampoo Anti Pulgas", BigDecimal.valueOf(19.98),null);
        when(produtoRepository.findById(3L)).thenReturn(Optional.of(produto));
        underTest.atualizarDescricaoOuValor(3L, null, valor);
        verify(produtoRepository).save(produto);
        verify(produtoRepository).findById(3L);
    }

    @Test
    void deveAtualizarDescricaoValor() {
        Long id = 3L;
        String descricao = "Shampoo Pelo Preto";
        BigDecimal valor = BigDecimal.valueOf(35.52);
        Produto produto = new Produto(3L, "Shampoo Anti Pulgas", BigDecimal.valueOf(19.98),null);
        when(produtoRepository.findById(3L)).thenReturn(Optional.of(produto));
        underTest.atualizarDescricaoOuValor(3L, descricao, valor);
        verify(produtoRepository).save(produto);
        verify(produtoRepository).findById(3L);
    }

    @Test
    void deveListar() {
        when(produtoRepository.findAll())
                .thenReturn(List.of(ProdutoTestFactory.criarShampoo(),
                ProdutoTestFactory.criarPastaDeDente()
        ));
        List<ProdutoConsulta> produtos = underTest.listar();
        assertEquals(2, produtos.size());
        assertThat(produtos).extracting("descricao")
                .containsExactly("Shampoo Anti Pulgas", "Pasta de Dente");
    }

}