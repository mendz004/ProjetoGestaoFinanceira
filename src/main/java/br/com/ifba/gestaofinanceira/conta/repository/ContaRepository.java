package br.com.ifba.gestaofinanceira.conta.repository;

import br.com.ifba.gestaofinanceira.conta.entity.Conta;
import br.com.ifba.gestaofinanceira.receita.entity.Receita;
import br.com.ifba.gestaofinanceira.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

    List<Conta> findByUsuarioId(Long usuarioId);

    Long usuario(Usuario usuario);

    Optional<Conta> findByIdAndUsuarioId(Long id, Long usuarioId);
}
