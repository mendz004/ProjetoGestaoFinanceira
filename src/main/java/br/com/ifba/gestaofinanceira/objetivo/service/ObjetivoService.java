package br.com.ifba.gestaofinanceira.objetivo.service;

import br.com.ifba.gestaofinanceira.Infraestructure.exception.BusinessException;
import br.com.ifba.gestaofinanceira.objetivo.dto.ObjetivoPostDto;
import br.com.ifba.gestaofinanceira.objetivo.emum.StatusObjetivo;
import br.com.ifba.gestaofinanceira.objetivo.entity.ObjetivoFinanceiro;
import br.com.ifba.gestaofinanceira.objetivo.repository.ObjetivoRepository;
import br.com.ifba.gestaofinanceira.receita.entity.Receita;
import br.com.ifba.gestaofinanceira.usuario.entity.Usuario;
import br.com.ifba.gestaofinanceira.usuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObjetivoService implements ObjetivoIService {

    @Autowired
    private ObjetivoRepository objetivoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    @Override
    public ObjetivoFinanceiro cadastrarObjetivo(ObjetivoPostDto dto, Long usuarioId) {
        if (dto.getValorAlvo() <= 0) {
            throw new BusinessException("O valor alvo deve ser maior que zero.");
        }

        // Busca o usuário no banco de dados
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado."));

        ObjetivoFinanceiro objetivo = new ObjetivoFinanceiro();

        objetivo.setNome(dto.getNome());
        objetivo.setValorAlvo(dto.getValorAlvo());
        objetivo.setValorAtual(dto.getValorAtual());
        objetivo.setStatus(dto.getStatus());
        objetivo.setDataLimite(dto.getDataLimite());
        objetivo.setUsuario(usuario);

        objetivo.setUsuario(usuario);

        return objetivoRepository.save(objetivo);
    }

    @Override
    public List<ObjetivoFinanceiro> findAll(Long usuarioId) {

        return objetivoRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public ObjetivoFinanceiro findByIdAndUsuario(Long id, Long usuarioId) {

        return objetivoRepository.findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new BusinessException("Objetivo não encontrada ou acesso negado."));
    }

    @Transactional
    @Override
    public ObjetivoFinanceiro depositar(Long id, Double valorDepositado, Long usuarioId) {
        if (valorDepositado <= 0) {
            throw new BusinessException("O valor do depósito deve ser maior que zero.");
        }

        ObjetivoFinanceiro objetivo = findByIdAndUsuario(id, usuarioId);

        // Adiciona o dinheiro no objetivo
        objetivo.setValorAtual(objetivo.getValorAtual() + valorDepositado);

        // Verifica se a meta foi alcançada ou ultrapassada
        if (objetivo.getValorAtual() >= objetivo.getValorAlvo()) {
            objetivo.setStatus(StatusObjetivo.CONCLUIDO);
        }

        return objetivoRepository.save(objetivo);
    }

    @Transactional
    @Override
    public ObjetivoFinanceiro atualizar(Long id, ObjetivoFinanceiro objetivoAtualizado, Long usuarioId) {

        ObjetivoFinanceiro objetivoExistente = findByIdAndUsuario(id, usuarioId);

        // Atualiza os dados da meta
        objetivoExistente.setNome(objetivoAtualizado.getNome());
        objetivoExistente.setValorAlvo(objetivoAtualizado.getValorAlvo());
        objetivoExistente.setValorAtual(objetivoAtualizado.getValorAtual());
        objetivoExistente.setDataLimite(objetivoAtualizado.getDataLimite());
        objetivoExistente.setStatus(objetivoAtualizado.getStatus());

        return objetivoRepository.save(objetivoExistente);
    }

    @Transactional
    @Override
    public void deleteById(Long id, Long usuarioId) {

        ObjetivoFinanceiro objetivo = findByIdAndUsuario(id, usuarioId);

        objetivoRepository.delete(objetivo);
    }
}
