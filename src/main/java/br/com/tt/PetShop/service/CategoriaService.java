package br.com.tt.PetShop.service;

import br.com.tt.PetShop.dto.CategoriaCriacao;
import br.com.tt.PetShop.model.Categoria;
import br.com.tt.PetShop.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    //TODO escrever os testes

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    //criar
    public Long criar(CategoriaCriacao criacao) {
        Categoria categoria = new Categoria(null, criacao.getNome(), null);
        Categoria categoriaSalvo = categoriaRepository.save(categoria);
        return categoriaSalvo.getId();
    }

    //achar por Id
    public Categoria acharPorId(Long id) {
        return categoriaRepository.findById(id).get();
    }

    //apagar
    public void apagar(Long id) {
        categoriaRepository.deleteById(id);
    }

}
