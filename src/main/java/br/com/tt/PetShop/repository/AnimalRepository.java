package br.com.tt.PetShop.repository;

import br.com.tt.PetShop.dto.AnimalConsulta;
import br.com.tt.PetShop.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

    List<Animal> findByTutorCpf(String cpf);

}
