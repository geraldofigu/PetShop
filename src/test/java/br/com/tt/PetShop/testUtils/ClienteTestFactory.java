package br.com.tt.PetShop.testUtils;

import br.com.tt.PetShop.model.Animal;
import br.com.tt.PetShop.model.Cliente;
import br.com.tt.PetShop.model.TipoAnimal;

import java.time.LocalDate;
import java.util.List;

public class ClienteTestFactory {

    public static Animal criaFreud() {
        return new Animal(3L, "Freud", LocalDate.of(2014, 11, 8), TipoAnimal.CAO, null);
    }

    public static Animal criaPanda() {
        return new Animal(4L, "Panda", LocalDate.of(2002, 4, 22), TipoAnimal.CAO, null);
    }

    public static Cliente criaGeraldo() {
        return new Cliente(1L, "Geraldo", "92316642047", List.of(criaFreud(), criaPanda()));
    }

    public static Cliente criaMargarete() {
        return new Cliente(2L, "Margarete", "34699340024", null);
    }

    public static Cliente criaRenata() {
        return new Cliente(3L, "Renata", "84355517098", null);
    }

}
