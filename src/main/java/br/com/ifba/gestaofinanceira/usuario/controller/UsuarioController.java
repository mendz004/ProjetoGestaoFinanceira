package br.com.ifba.gestaofinanceira.usuario.controller;

import br.com.ifba.gestaofinanceira.Infraestructure.mapper.ObjectMapperUtil;
import br.com.ifba.gestaofinanceira.usuario.dto.UsuarioGetDto;
import br.com.ifba.gestaofinanceira.usuario.dto.UsuarioPostDto;
import br.com.ifba.gestaofinanceira.usuario.entity.Usuario;
import br.com.ifba.gestaofinanceira.usuario.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    public ResponseEntity<UsuarioGetDto> login(@RequestBody UsuarioPostDto usuarioPostDto) {

            Usuario usuarioLogado = usuarioService.login(usuarioPostDto.getEmail(), usuarioPostDto.getSenha());

            return ResponseEntity.status(HttpStatus.OK).body(objectMapperUtil.map(usuarioLogado, UsuarioGetDto.class));

    }
    

    // Recuperar senha
    @PutMapping("/recuperar-senha")
    public ResponseEntity<String> recuperarSenha(@RequestBody UsuarioPostDto usuarioPostDto) {

            usuarioService.recuperarSenha(usuarioPostDto.getEmail(), usuarioPostDto.getSenha());

            return ResponseEntity.ok("Senha atualizada com sucesso!");


    }

    }
