package br.com.tt.PetShop.testUtils;

import br.com.tt.PetShop.model.Categoria;
import br.com.tt.PetShop.model.Produto;

import java.math.BigDecimal;

public class ProdutoTestFactory {

    public static Categoria criarHigiene() {
        return new Categoria(3L, "Higiene", null);
    }

    public static Produto criarShampoo() {
        return new Produto(1L, "Shampoo Anti Pulgas", BigDecimal.valueOf(19.98), criarHigiene());
    }

    public static Produto criarPastaDeDente() {
        return new Produto(2L, "Pasta de Dente", BigDecimal.valueOf(5.12), criarHigiene());
    }

}
