package br.com.tt.PetShop.mapper;

import br.com.tt.PetShop.dto.AnimalConsulta;
import br.com.tt.PetShop.dto.AnimalCriacao;
import br.com.tt.PetShop.model.Animal;
import br.com.tt.PetShop.model.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AnimalMapper {

    AnimalMapper INSTANCE = Mappers.getMapper(AnimalMapper.class);

    @Mapping(ignore = true, target = "id")
    Animal fromCriar(AnimalCriacao criacao);


    @Mapping(target = "tutor", expression = "java(getTutor(animal))")
    AnimalConsulta converterConsulta(Animal animal);


    default String getTutor(Animal animal) {
        return animal.getTutor().getNome();
    }
}
