package br.com.ifba.gestaofinanceira.orcamento.service;

import br.com.ifba.gestaofinanceira.Infraestructure.exception.BusinessException;
import br.com.ifba.gestaofinanceira.orcamento.dto.OrcamentoPostDto;
import br.com.ifba.gestaofinanceira.orcamento.entity.Orcamento;
import br.com.ifba.gestaofinanceira.orcamento.enun.CategoriaDespesa;
import br.com.ifba.gestaofinanceira.orcamento.repository.OrcamentoRepository;
import br.com.ifba.gestaofinanceira.usuario.entity.Usuario;
import br.com.ifba.gestaofinanceira.usuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrcamentoService implements OrcamentoIService {


    @Autowired
    private OrcamentoRepository orcamentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    @Override
    public Orcamento cadastrarOrcamento(OrcamentoPostDto dto) {
        if (dto.getValorLimite() <= 0) {
            throw new BusinessException("O valor limite do orçamento deve ser maior que zero.");
        }

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new BusinessException("Usuário não encontrado."));

        // Evita que o usuário crie dois orçamentos para a mesma categoria no mesmo mês
        Optional<Orcamento> orcamentoExistente = orcamentoRepository
                .findByUsuarioIdAndMesAndAnoAndCategoria(dto.getUsuarioId(), dto.getMes(), dto.getAno(), dto.getCategoria());

        if (orcamentoExistente.isPresent()) {
            throw new BusinessException("Já existe um orçamento definido para esta categoria neste mês e ano.");
        }

        Orcamento orcamento = new Orcamento();

        orcamento.setMes(dto.getMes());
        orcamento.setAno(dto.getAno());
        orcamento.setCategoria(dto.getCategoria());
        orcamento.setValorLimite(dto.getValorLimite());

        orcamento.setValorGasto(0.0);
        orcamento.setUsuario(usuario);

        return orcamentoRepository.save(orcamento);
    }

    @Override
    public List<Orcamento> findAll() {
        return orcamentoRepository.findAll();
    }

    // Gera uma sugestão de limites baseada na regra 50/30/20
    @Override
    public Map<CategoriaDespesa, Double> gerarSugestaoOrcamento(Long usuarioId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado."));

        Double rendaMensal = usuario.getRendaMensal();

        if (rendaMensal == null) {
            throw new BusinessException("O usuário não possui renda mensal cadastrada.");
        }

        Map<CategoriaDespesa, Double> sugestoes = new EnumMap<>(CategoriaDespesa.class);

        // 50% para Necessidades
        Double necessidades = rendaMensal * 0.50;
        sugestoes.put(CategoriaDespesa.MORADIA, necessidades * 0.50);    // 25% da renda
        sugestoes.put(CategoriaDespesa.ALIMENTACAO, necessidades * 0.30); // 15% da renda
        sugestoes.put(CategoriaDespesa.TRANSPORTE, necessidades * 0.10);  // 5% da renda
        sugestoes.put(CategoriaDespesa.SAUDE, necessidades * 0.10);       // 5% da renda

        // 30% para Desejos (Estilo de vida)
        Double desejos = rendaMensal * 0.30;
        sugestoes.put(CategoriaDespesa.LAZER, desejos * 0.60);            // 18% da renda
        sugestoes.put(CategoriaDespesa.OUTROS, desejos * 0.40);           // 12% da renda

        // 20% para Futuro (Investimentos / Educação)
        Double futuro = rendaMensal * 0.20;
        sugestoes.put(CategoriaDespesa.INVESTIMENTOS, futuro * 0.75);     // 15% da renda
        sugestoes.put(CategoriaDespesa.EDUCACAO, futuro * 0.25);          // 5% da renda

        return sugestoes;

    }

    @Transactional
    @Override
    public void atualizarGasto(Long usuarioId, Integer mes, Integer ano, CategoriaDespesa categoria, Double valorGasto) {

        Optional<Orcamento> orcamentoOpt = orcamentoRepository
                .findByUsuarioIdAndMesAndAnoAndCategoria(usuarioId, mes, ano, categoria);

        if (orcamentoOpt.isPresent()) {
            Orcamento orcamento = orcamentoOpt.get();
            orcamento.setValorGasto(orcamento.getValorGasto() + valorGasto);
            orcamentoRepository.save(orcamento);
        }
    }

    @Transactional
    @Override
    public Orcamento atualizar(Long id, Orcamento orcamentoAtualizado) {

        Orcamento orcamentoExistente = orcamentoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Orçamento não encontrado com o ID: " + id));

        orcamentoExistente.setCategoria(orcamentoAtualizado.getCategoria());
        orcamentoExistente.setValorLimite(orcamentoAtualizado.getValorLimite());
        orcamentoExistente.setValorGasto(orcamentoAtualizado.getValorGasto());
        orcamentoExistente.setMes(orcamentoAtualizado.getMes());
        orcamentoExistente.setAno(orcamentoAtualizado.getAno());

        return orcamentoRepository.save(orcamentoExistente);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        Orcamento orcamento = orcamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orçamento não encontrado."));

        orcamentoRepository.delete(orcamento);
    }
}
