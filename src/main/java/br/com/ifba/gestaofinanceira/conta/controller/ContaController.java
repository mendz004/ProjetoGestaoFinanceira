package br.com.ifba.gestaofinanceira.conta.controller;

import br.com.ifba.gestaofinanceira.conta.dto.ContaGetDto;
import br.com.ifba.gestaofinanceira.conta.dto.ContaPostDto;
import br.com.ifba.gestaofinanceira.conta.entity.Conta;
import br.com.ifba.gestaofinanceira.conta.service.ContaService;
import br.com.ifba.gestaofinanceira.Infraestructure.mapper.ObjectMapperUtil;
import br.com.ifba.gestaofinanceira.usuario.entity.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @Autowired
    private ObjectMapperUtil objectMapperUtil;

    @PostMapping
    public ResponseEntity<ContaGetDto> cadastrarConta(@RequestBody @Valid ContaPostDto dto) {

        Long usuarioId = getUsuarioLogadoId();
        Conta novaConta = contaService.cadastrarConta(dto, usuarioId);

        ContaGetDto resposta = objectMapperUtil.map(novaConta, ContaGetDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @GetMapping
    public ResponseEntity<List<ContaGetDto>> findAll() {

        Long usuarioId = getUsuarioLogadoId();

        return ResponseEntity.status(HttpStatus.OK).body(objectMapperUtil.mapAll
                (this.contaService.findAllByUsuario(usuarioId),
                        ContaGetDto.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {

        Long usuarioId = getUsuarioLogadoId();

        contaService.deleteById(id, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Conta> atualizar(@PathVariable Long id, @RequestBody Conta conta) {

        Long usuarioId = getUsuarioLogadoId();

        Conta contaAtualizada = contaService.atualizar(id, conta, usuarioId);
        return ResponseEntity.ok(contaAtualizada);
    }

    private Long getUsuarioLogadoId() {
        // Extrai o usuário que o SecurityFilter validou a partir do JWT
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return usuarioLogado.getId();
    }

}
