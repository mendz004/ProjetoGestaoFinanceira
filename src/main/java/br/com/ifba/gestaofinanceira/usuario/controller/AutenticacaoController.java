package br.com.ifba.gestaofinanceira.usuario.controller;

import br.com.ifba.gestaofinanceira.Infraestructure.security.TokenService;
import br.com.ifba.gestaofinanceira.usuario.dto.DadosAutenticacao;
import br.com.ifba.gestaofinanceira.usuario.dto.TokenJwtDto;
import br.com.ifba.gestaofinanceira.usuario.entity.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        // Cria um token do próprio Spring com o email e senha recebidos
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());

        // Chama a autenticação (isso dispara o AutenticacaoService que criamos no Passo 1)
        var authentication = manager.authenticate(authenticationToken);

        // Se a senha estiver correta, gera o Token JWT
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        // Devolve o token em formato JSON para o Front-end
        return ResponseEntity.ok(new TokenJwtDto(tokenJWT));
    }
}
