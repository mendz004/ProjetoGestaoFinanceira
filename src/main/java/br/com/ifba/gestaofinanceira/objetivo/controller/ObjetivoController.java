package br.com.ifba.gestaofinanceira.objetivo.controller;

import br.com.ifba.gestaofinanceira.Infraestructure.mapper.ObjectMapperUtil;
import br.com.ifba.gestaofinanceira.objetivo.dto.ObjetivoGetDto;
import br.com.ifba.gestaofinanceira.objetivo.dto.ObjetivoPostDto;
import br.com.ifba.gestaofinanceira.objetivo.entity.ObjetivoFinanceiro;
import br.com.ifba.gestaofinanceira.objetivo.service.ObjetivoService;
import br.com.ifba.gestaofinanceira.usuario.entity.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/objetivos")
public class ObjetivoController {

    @Autowired
    private ObjetivoService service;

    @Autowired
    private ObjectMapperUtil objectMapperUtil;

    @PostMapping
    public ResponseEntity<ObjetivoGetDto> cadastrar(@RequestBody @Valid ObjetivoPostDto dto) {

        Long usuarioId = getUsuarioLogadoId();
        ObjetivoFinanceiro novoObjetivo = service.cadastrarObjetivo(dto, usuarioId);

        ObjetivoGetDto resposta = objectMapperUtil.map(novoObjetivo, ObjetivoGetDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @GetMapping
    public ResponseEntity<List<ObjetivoGetDto>> findAll() {

        Long usuarioId = getUsuarioLogadoId();

        return ResponseEntity.status(HttpStatus.OK).body(
                objectMapperUtil.mapAll(service.findAll(usuarioId), ObjetivoGetDto.class)
        );
    }

    @PutMapping("/{id}/depositar")
    public ResponseEntity<ObjetivoGetDto> depositar(@PathVariable Long id, @RequestParam Double valor) {

        Long usuarioId = getUsuarioLogadoId();

        ObjetivoFinanceiro objetivoAtualizado = service.depositar(id, valor, usuarioId);

        ObjetivoGetDto resposta = objectMapperUtil.map(objetivoAtualizado, ObjetivoGetDto.class);

        return ResponseEntity.status(HttpStatus.OK).body(resposta);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ObjetivoFinanceiro> atualizar(@PathVariable Long id, @RequestBody ObjetivoFinanceiro objetivo) {

        Long usuarioId = getUsuarioLogadoId();

        ObjetivoFinanceiro objetivoAtualizado = service.atualizar(id, objetivo, usuarioId);
        return ResponseEntity.ok(objetivoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {

        Long usuarioId = getUsuarioLogadoId();

        service.deleteById(id, usuarioId);
        return ResponseEntity.noContent().build();
    }

    private Long getUsuarioLogadoId() {
        // Extrai o usuário que o SecurityFilter validou a partir do JWT
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return usuarioLogado.getId();
    }
}
