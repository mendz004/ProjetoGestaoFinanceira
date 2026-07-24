package br.com.ifba.gestaofinanceira.cartaoCredito.repository;

import br.com.ifba.gestaofinanceira.cartaoCredito.entity.Cartao;
import br.com.ifba.gestaofinanceira.despesa.entity.Despesa;
import br.com.ifba.gestaofinanceira.receita.entity.Receita;
import br.com.ifba.gestaofinanceira.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> {

    List<Cartao> findByUsuarioId(Long usuarioId);

    Long usuario(Usuario usuario);

    Optional<Cartao> findByIdAndUsuarioId(Long id, Long usuarioId);

}
