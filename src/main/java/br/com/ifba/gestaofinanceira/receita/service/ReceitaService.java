package br.com.ifba.gestaofinanceira.receita.service;

import br.com.ifba.gestaofinanceira.Infraestructure.exception.BusinessException;
import br.com.ifba.gestaofinanceira.cartaoCredito.entity.Cartao;
import br.com.ifba.gestaofinanceira.conta.entity.Conta;
import br.com.ifba.gestaofinanceira.conta.repository.ContaRepository;
import br.com.ifba.gestaofinanceira.despesa.entity.Despesa;
import br.com.ifba.gestaofinanceira.receita.dto.ReceitaPostDto;
import br.com.ifba.gestaofinanceira.receita.entity.Receita;
import br.com.ifba.gestaofinanceira.receita.repository.ReceitaRepository;
import br.com.ifba.gestaofinanceira.usuario.entity.Usuario;
import br.com.ifba.gestaofinanceira.usuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceitaService implements ReceitaIService {

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    @Override
    public Receita cadastrarReceita(ReceitaPostDto dto, Long usuarioId) {
        //  Busca a conta no banco de dados
        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new BusinessException("Conta não encontrada."));

        // Busca a entidade do usuário no banco
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado."));

        Double saldoAtual = conta.getSaldoAtual() != null ? conta.getSaldoAtual() : 0.0;
        conta.setSaldoAtual(saldoAtual + dto.getValor());
        contaRepository.save(conta); // Salva a conta com o novo saldo

        Receita receita = new Receita();
        receita.setValor(dto.getValor());
        receita.setData(dto.getData());
        receita.setDescricao(dto.getDescricao());
        receita.setOrigem(dto.getOrigem());
        receita.setConta(conta);

        receita.setUsuario(usuario);

        return receitaRepository.save(receita);
    }

    @Override
    public List<Receita> findAllByUsuario(Long usuarioId) {
        return receitaRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public Receita findByIdAndUsuario(Long id, Long usuarioId) {
        return receitaRepository.findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new BusinessException("Receita não encontrada ou acesso negado."));
    }

    @Transactional
    @Override
    public void deleteById(Long id, Long usuarioId) {
        Receita receita = findByIdAndUsuario(id, usuarioId);

        //  Subtrai o valor da conta antes de apagar a receita
        if (receita.getConta() != null) {
            Conta conta = receita.getConta();
            conta.setSaldoAtual(conta.getSaldoAtual() - receita.getValor());
            contaRepository.save(conta);
        }

        // Deleta a receita do banco de dados
        receitaRepository.delete(receita);
    }

    @Transactional
    @Override
    public Receita atualizar(Long id, Receita novaReceita, Long usuarioId) {

        Receita receitaExistente = findByIdAndUsuario(id, usuarioId);

        //  REVERTE o impacto antigo (Tira o dinheiro da conta antiga)
        if (receitaExistente.getConta() != null) {
            Conta contaAntiga = receitaExistente.getConta();
            contaAntiga.setSaldoAtual(contaAntiga.getSaldoAtual() - receitaExistente.getValor());
            contaRepository.save(contaAntiga);
        }

        //  APLICA o novo impacto (Coloca o novo dinheiro na nova conta)
        if (novaReceita.getConta() != null) {
            // Busca a conta atualizada para garantir que temos a versão mais recente do banco
            Conta contaNova = contaRepository.findById(novaReceita.getConta().getId())
                    .orElseThrow(() -> new BusinessException("Conta de destino não encontrada."));

            contaNova.setSaldoAtual(contaNova.getSaldoAtual() + novaReceita.getValor());
            receitaExistente.setConta(contaNova);
            contaRepository.save(contaNova);
        }

        //  Atualiza os dados da receita
        receitaExistente.setDescricao(novaReceita.getDescricao());
        receitaExistente.setValor(novaReceita.getValor());
        receitaExistente.setData(novaReceita.getData());
        receitaExistente.setOrigem(novaReceita.getOrigem());

        return receitaRepository.save(receitaExistente);
    }

    @Override
    public List<Receita> findByDescricao(String termo, Long usuarioId) {
        return receitaRepository.findByDescricaoContainingIgnoreCaseAndUsuarioId(termo, usuarioId);
    }

}
