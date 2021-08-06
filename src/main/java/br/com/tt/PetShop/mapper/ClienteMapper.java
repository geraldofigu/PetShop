package br.com.tt.PetShop.mapper;

import br.com.tt.PetShop.dto.ClienteBuscaCpf;
import br.com.tt.PetShop.dto.ClienteConsulta;
import br.com.tt.PetShop.dto.ClienteCriacao;
import br.com.tt.PetShop.model.Animal;
import br.com.tt.PetShop.model.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "animais")
    Cliente fromCriar(ClienteCriacao criacao);

    ClienteConsulta converterConsulta(Cliente cliente);

    @Mapping(target = "animais", expression = "java(getAnimais(cliente))")
    ClienteBuscaCpf converterConsultaCpf(Cliente cliente);

    default List<String> getAnimais(Cliente cliente) {
        return cliente.getAnimais().stream()
                .map(Animal::getApelido)
                .collect(Collectors.toList());
    }

}
