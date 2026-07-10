package br.com.ifba.gestaofinanceira.conta.service;

import br.com.ifba.gestaofinanceira.conta.dto.ContaPostDto;
import br.com.ifba.gestaofinanceira.conta.entity.Conta;
import br.com.ifba.gestaofinanceira.conta.repository.ContaRepository;
import br.com.ifba.gestaofinanceira.Infraestructure.exception.BusinessException;
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

    @Transactional
    @Override
    public Conta cadastrarConta(ContaPostDto dto) {

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

        Conta conta = new Conta();
        conta.setNomeConta(dto.getNomeConta());
        conta.setSaldoAtual(dto.getSaldoAtual());
        conta.setTipo(dto.getTipo());
        conta.setUsuario(usuario);

        return contaRepository.save(conta);
    }

    public List<Conta> findAll() {
        return contaRepository.findAll();
    }


}
