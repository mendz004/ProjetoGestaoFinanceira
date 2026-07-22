package br.com.ifba.gestaofinanceira.usuario.service;

import br.com.ifba.gestaofinanceira.Infraestructure.exception.BusinessException;
import br.com.ifba.gestaofinanceira.usuario.entity.Usuario;
import br.com.ifba.gestaofinanceira.usuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UsuarioIService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public Usuario cadastrar(Usuario usuario) {
        boolean emailExistente = usuarioRepository.existsByEmail(usuario.getEmail());

        if (emailExistente) {
            throw new BusinessException("Email já Cadastrado");
        }

        //  Criptografa a senha antes de salvar no banco!
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);

        return usuarioRepository.save(usuario);
    }


    @Transactional
    @Override
    public void recuperarSenha(String email, String novaSenha) {

        Usuario usuario = (Usuario) usuarioRepository.findByEmail(email);

        if (usuario == null) {
            throw new BusinessException("E-mail não encontrado no sistema.");
        }

        //  Criptografa a nova senha antes de atualizar no banco!
        String senhaCriptografada = passwordEncoder.encode(novaSenha);
        usuario.setSenha(senhaCriptografada);

        usuarioRepository.save(usuario);
    }
}