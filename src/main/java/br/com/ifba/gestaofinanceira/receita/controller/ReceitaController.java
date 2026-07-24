package br.com.ifba.gestaofinanceira.receita.controller;

import br.com.ifba.gestaofinanceira.Infraestructure.mapper.ObjectMapperUtil;
import br.com.ifba.gestaofinanceira.receita.dto.ReceitaGetDto;
import br.com.ifba.gestaofinanceira.receita.dto.ReceitaPostDto;
import br.com.ifba.gestaofinanceira.receita.entity.Receita;
import br.com.ifba.gestaofinanceira.receita.service.ReceitaService;
import br.com.ifba.gestaofinanceira.usuario.entity.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {

    @Autowired
    private ReceitaService receitaService;

    @Autowired
    private ObjectMapperUtil objectMapperUtil;

    @PostMapping
    public ResponseEntity<ReceitaGetDto> cadastrarReceita(@RequestBody @Valid ReceitaPostDto receitaPostDto) {

        Long usuarioId = getUsuarioLogadoId();
        Receita novaReceita = receitaService.cadastrarReceita(receitaPostDto, usuarioId);

        ReceitaGetDto resposta = objectMapperUtil.map(novaReceita, ReceitaGetDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @GetMapping
    public ResponseEntity<List<ReceitaGetDto>> findAll() {

        Long usuarioId = getUsuarioLogadoId();
        return ResponseEntity.status(HttpStatus.OK).body(objectMapperUtil.mapAll
                (this.receitaService.findAllByUsuario(usuarioId),
                        ReceitaGetDto.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirDespesa(@PathVariable Long id) {

        Long usuarioId = getUsuarioLogadoId();
        receitaService.deleteById(id, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Receita> atualizar(@PathVariable Long id, @RequestBody Receita receita) {

        Long usuarioId = getUsuarioLogadoId();
        Receita receitaAtualizada = receitaService.atualizar(id, receita, usuarioId);
        return ResponseEntity.ok(receitaAtualizada);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Receita>> buscar(@RequestParam String termo) {

        Long usuarioId = getUsuarioLogadoId();
        List<Receita> receitas = receitaService.findByDescricao(termo, usuarioId);
        return ResponseEntity.ok(receitas);
    }

    private Long getUsuarioLogadoId() {
        // Extrai o usuário que o SecurityFilter validou a partir do JWT
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return usuarioLogado.getId();
    }
}
