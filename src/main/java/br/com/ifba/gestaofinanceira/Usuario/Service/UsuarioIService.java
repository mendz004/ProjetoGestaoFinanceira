package br.com.ifba.gestaofinanceira.Usuario.Service;

import br.com.ifba.gestaofinanceira.Usuario.Entity.Usuario;

import java.util.List;

public interface UsuarioIService {

    Usuario cadastrar(Usuario usuario);

    Usuario login(String email, String senha);

    void recuperarSenha(String email, String novaSenha);


}
