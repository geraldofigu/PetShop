package br.com.tt.PetShop.repository;

import br.com.tt.PetShop.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
