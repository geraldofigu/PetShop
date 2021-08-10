package br.com.tt.PetShop.service;

import br.com.tt.PetShop.dto.CategoriaCriacao;
import br.com.tt.PetShop.model.Categoria;
import br.com.tt.PetShop.repository.CategoriaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    CategoriaRepository categoriaRepository;

    @InjectMocks
    CategoriaService underTest;

    @Test
    void deveCriar() {
        CategoriaCriacao criacao = new CategoriaCriacao("Ração");
//        Categoria categoria = new Categoria(null, criacao.getNome(), null);
        Categoria categoriaSalvo = new Categoria(45L, "Perfume", null);
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoriaSalvo);
        Long id = underTest.criar(criacao);
        assertEquals(45L, id);
        verify(categoriaRepository).save(any(Categoria.class));
    }

    @Test
    void deveAcharPorId() {
        when(categoriaRepository.findById(25L))
                .thenReturn(Optional.of(new Categoria(25L, "Perfumes", null)));
        Categoria categoria = underTest.acharPorId(25L);
        assertEquals("Perfumes", categoria.getNome());
        verify(categoriaRepository).findById(25L);
    }

    @Test
    void deveApagar() {
        underTest.apagar(any());
        verify(categoriaRepository).deleteById(any());
    }

}