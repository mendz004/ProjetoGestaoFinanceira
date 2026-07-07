package br.com.ifba.gestaofinanceira.usuario.service;

import br.com.ifba.gestaofinanceira.Infraestructure.exception.BusinessException;
import br.com.ifba.gestaofinanceira.usuario.entity.Usuario;
import br.com.ifba.gestaofinanceira.usuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService implements UsuarioIService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    @Override
    public Usuario cadastrar(Usuario usuario) {
        boolean emailExistente = usuarioRepository.existsByEmail(usuario.getEmail());

        if (emailExistente) {
            throw new RuntimeException("Email já Cadastrado");
        } else
            return usuarioRepository.save(usuario);

    }

    @Transactional
    @Override
    public Usuario login(String email, String senha) {

        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isEmpty()) {
            throw new BusinessException("E-mail não cadastrado.");
        }

        Usuario usuario = usuarioOpt.get();

        if (!usuario.getSenha().equals(senha)) {
            throw new BusinessException("Senha incorreta.");
        }

        return usuario;
    }


    @Override
    public void recuperarSenha(String email, String novaSenha) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isEmpty()) {
            throw new BusinessException("E-mail não encontrado no sistema.");
        }

        Usuario usuario = usuarioOpt.get();

        usuario.setSenha(novaSenha);

        usuarioRepository.save(usuario);
    }

}
