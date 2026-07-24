package br.com.ifba.gestaofinanceira.conta.service;

import br.com.ifba.gestaofinanceira.conta.dto.ContaPostDto;
import br.com.ifba.gestaofinanceira.conta.entity.Conta;
import br.com.ifba.gestaofinanceira.conta.repository.ContaRepository;
import br.com.ifba.gestaofinanceira.Infraestructure.exception.BusinessException;
import br.com.ifba.gestaofinanceira.despesa.repository.DespesaRepository;
import br.com.ifba.gestaofinanceira.receita.entity.Receita;
import br.com.ifba.gestaofinanceira.receita.repository.ReceitaRepository;
import br.com.ifba.gestaofinanceira.usuario.entity.Usuario;
import br.com.ifba.gestaofinanceira.usuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContaService implements ContaIService{

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private ReceitaRepository receitaRepository;

    @Transactional
    @Override
    public Conta cadastrarConta(ContaPostDto dto, Long usuarioId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

        Conta conta = new Conta();
        conta.setNomeConta(dto.getNomeConta());
        conta.setSaldoAtual(dto.getSaldoAtual());
        conta.setTipo(dto.getTipo());
        conta.setUsuario(usuario);

        conta.setUsuario(usuario);

        return contaRepository.save(conta);
    }

    @Override
    public List<Conta> findAllByUsuario(Long usuarioId) {
        return contaRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public Conta findByIdAndUsuario(Long id, Long usuarioId) {
        return contaRepository.findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new BusinessException("Conta não encontrada ou acesso negado."));
    }

    @Transactional
    @Override
    public void deleteById(Long id, Long usuarioId) {

        Conta conta = contaRepository.findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new BusinessException("Conta não encontrada ou você não tem permissão para excluí-la."));

        despesaRepository.deleteAllByConta(conta);
        receitaRepository.deleteAllByConta(conta);

        contaRepository.delete(conta);
    }

    @Transactional
    @Override
    public Conta atualizar(Long id, Conta contaAtualizada, Long usuarioId) {

        Conta contaExistente = contaRepository.findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new BusinessException("Conta não encontrada."));

        // Atualiza apenas os dados permitidos
        contaExistente.setNomeConta(contaAtualizada.getNomeConta());
        contaExistente.setTipo(contaAtualizada.getTipo());
        contaExistente.setSaldoAtual(contaAtualizada.getSaldoAtual());

        return contaRepository.save(contaExistente);
    }
}
