package br.com.tt.PetShop.repository;

import br.com.tt.PetShop.dto.ProdutoConsulta;
import br.com.tt.PetShop.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByValorBetween(BigDecimal min, BigDecimal max);
}

