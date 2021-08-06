package br.com.tt.PetShop.mapper;

import br.com.tt.PetShop.dto.ProdutoConsulta;
import br.com.tt.PetShop.dto.ProdutoCriacao;
import br.com.tt.PetShop.model.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProdutoMapper {

    ProdutoMapper INSTANCE = Mappers.getMapper(ProdutoMapper.class);

//    @Mapping(ignore = true, target = "id")
//    @Mapping(ignore = true, target = "categoria")
//    Produto fromCriar(ProdutoCriacao criacao);

    @Mapping(target = "categoria", expression = "java(getNome(produto))")
    ProdutoConsulta converterConsulta(Produto produto);

    default String getNome(Produto produto) {
        return produto.getCategoria().getNome();
    }

}
