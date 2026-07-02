package br.com.ifba.gestaofinanceira.Usuario.Controller;

import br.com.ifba.gestaofinanceira.Conta.Dto.ContaGetDto;
import br.com.ifba.gestaofinanceira.Infraestructure.Mapper.ObjectMapperUtil;
import br.com.ifba.gestaofinanceira.Usuario.DTO.UsuarioGetDto;
import br.com.ifba.gestaofinanceira.Usuario.DTO.UsuarioPostDto;
import br.com.ifba.gestaofinanceira.Usuario.Entity.Usuario;
import br.com.ifba.gestaofinanceira.Usuario.Service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapperUtil objectMapperUtil;

    // Cadastrar Usuario
    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioGetDto> cadastrar(@RequestBody @Valid UsuarioPostDto usuarioPostDto) {
        Usuario usuarioSave = usuarioService.cadastrar(
                objectMapperUtil.map(usuarioPostDto, Usuario.class));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(objectMapperUtil.map(usuarioSave, UsuarioGetDto.class));
    }

    // Login Usuario
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioPostDto usuarioPostDto) {
        try {
            Usuario usuarioLogado = usuarioService.login(usuarioPostDto.getEmail(), usuarioPostDto.getSenha());

            // Retorna status 200 OK e os dados do usuário (idealmente, sem a senha)
            return ResponseEntity.ok(usuarioLogado);

        } catch (RuntimeException e) {
            // Retorna status 401 Unauthorized se der erro de credencial
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    

    // Recuperar senha
    @PutMapping("/recuperar-senha")
    public ResponseEntity<String> recuperarSenha(@RequestBody UsuarioPostDto usuarioPostDto) {
        try {
            usuarioService.recuperarSenha(usuarioPostDto.getEmail(), usuarioPostDto.getSenha());

            // Retorna status 200 OK com uma mensagem de sucesso
            return ResponseEntity.ok("Senha atualizada com sucesso!");

        } catch (RuntimeException e) {
            // Retorna status 404 Not Found se o e-mail não existir
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    }
