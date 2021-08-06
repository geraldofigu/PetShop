package br.com.tt.PetShop.testUtils;

import br.com.tt.PetShop.model.Animal;
import br.com.tt.PetShop.model.Cliente;
import br.com.tt.PetShop.model.TipoAnimal;

import java.time.LocalDate;

public class AnimalTestFactory {

    public static Cliente criaCliente() {
        return new Cliente(3L, "Geraldo", "01216381038", null);
    }

    public static Animal criaFreud() {
        return new Animal(12L, "Freud", LocalDate.of(2014,11,8), TipoAnimal.CAO, criaCliente());
    }
    public static Animal criaPanda() {
        return new Animal(9L, "Panda", LocalDate.of(2002,04,21), TipoAnimal.CAO, criaCliente());
    }
    public static Animal criaJulie() {
        return new Animal(10L, "Julie", LocalDate.of(2002,04,21), TipoAnimal.CAO, criaCliente());
    }



}
