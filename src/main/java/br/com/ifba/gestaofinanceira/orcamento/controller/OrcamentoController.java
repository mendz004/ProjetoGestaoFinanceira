package br.com.ifba.gestaofinanceira.orcamento.controller;

import br.com.ifba.gestaofinanceira.Infraestructure.mapper.ObjectMapperUtil;
import br.com.ifba.gestaofinanceira.orcamento.dto.OrcamentoGetDto;
import br.com.ifba.gestaofinanceira.orcamento.dto.OrcamentoPostDto;
import br.com.ifba.gestaofinanceira.orcamento.entity.Orcamento;
import br.com.ifba.gestaofinanceira.orcamento.enun.CategoriaDespesa;
import br.com.ifba.gestaofinanceira.orcamento.service.OrcamentoIService;
import br.com.ifba.gestaofinanceira.usuario.entity.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orcamentos")
public class OrcamentoController {

    @Autowired
    private OrcamentoIService orcamentoService;

    @Autowired
    private ObjectMapperUtil objectMapperUtil;

    @PostMapping
    public ResponseEntity<OrcamentoGetDto> cadastrar(@RequestBody @Valid OrcamentoPostDto dto) {

        Long usuarioId = getUsuarioLogadoId();
        Orcamento novoOrcamento = orcamentoService.cadastrarOrcamento(dto, usuarioId);

        OrcamentoGetDto resposta = objectMapperUtil.map(novoOrcamento, OrcamentoGetDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @GetMapping
    public ResponseEntity<List<OrcamentoGetDto>> findAll() {

        Long usuarioId = getUsuarioLogadoId();

        return ResponseEntity.status(HttpStatus.OK).body(
                objectMapperUtil.mapAll(orcamentoService.findAll(usuarioId), OrcamentoGetDto.class)
        );
    }

    @GetMapping("/sugestao/{usuarioId}")
    public ResponseEntity<Map<CategoriaDespesa, Double>> sugerir(@PathVariable Long usuarioId) {

        Map<CategoriaDespesa, Double> sugestao = orcamentoService.gerarSugestaoOrcamento(usuarioId);
        return ResponseEntity.status(HttpStatus.OK).body(sugestao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Orcamento> atualizar(@PathVariable Long id, @RequestBody Orcamento orcamento) {

        Long usuarioId = getUsuarioLogadoId();

        Orcamento orcamentoAtualizado = orcamentoService.atualizar(id, orcamento, usuarioId);
        return ResponseEntity.ok(orcamentoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {

        Long usuarioId = getUsuarioLogadoId();

        orcamentoService.deleteById(id, usuarioId);
        return ResponseEntity.noContent().build();
    }

    private Long getUsuarioLogadoId() {
        // Extrai o usuário que o SecurityFilter validou a partir do JWT
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return usuarioLogado.getId();
    }
}
