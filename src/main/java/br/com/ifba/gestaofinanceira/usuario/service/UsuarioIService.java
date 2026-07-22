package br.com.ifba.gestaofinanceira.usuario.service;

import br.com.ifba.gestaofinanceira.usuario.entity.Usuario;

public interface UsuarioIService {

    Usuario cadastrar(Usuario usuario);


    void recuperarSenha(String email, String novaSenha);


}
