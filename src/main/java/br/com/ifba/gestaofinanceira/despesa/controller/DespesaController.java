package br.com.ifba.gestaofinanceira.despesa.controller;

import br.com.ifba.gestaofinanceira.despesa.dto.DespesaGetDto;
import br.com.ifba.gestaofinanceira.despesa.dto.DespesaPostDto;
import br.com.ifba.gestaofinanceira.despesa.entity.Despesa;
import br.com.ifba.gestaofinanceira.despesa.service.DespesaService;
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
@RequestMapping("/despesas")
public class DespesaController {

    @Autowired
    private DespesaService despesaService;

    @Autowired
    private ObjectMapperUtil objectMapperUtil;


    @PostMapping
    public ResponseEntity<DespesaGetDto> registrarDespesa(@Valid @RequestBody DespesaPostDto despesaPostDto) {

        Long usuarioId = getUsuarioLogadoId();

        Despesa despesa = despesaService.registrarDespesa(despesaPostDto, usuarioId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(objectMapperUtil.map(despesa, DespesaGetDto.class));
    }

    @GetMapping
    public ResponseEntity<List<DespesaGetDto>> listarTodas() {

        Long usuarioId = getUsuarioLogadoId();

        return ResponseEntity.status(HttpStatus.OK).body(objectMapperUtil.mapAll
                (this.despesaService.findAll(usuarioId),
                        DespesaGetDto.class));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirDespesa(@PathVariable Long id) {

        Long usuarioId = getUsuarioLogadoId();

        despesaService.deleteById(id, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Despesa> atualizar(@PathVariable Long id, @RequestBody Despesa despesa) {

        Long usuarioId = getUsuarioLogadoId();

        Despesa despesaAtualizada = despesaService.atualizar(id, despesa, usuarioId);
        return ResponseEntity.ok(despesaAtualizada);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Despesa>> buscar(@RequestParam String termo) {

        Long usuarioId = getUsuarioLogadoId();

        List<Despesa> despesas = despesaService.findByDescricao(termo, usuarioId);
        return ResponseEntity.ok(despesas);
    }

    private Long getUsuarioLogadoId() {
        // Extrai o usuário que o SecurityFilter validou a partir do JWT
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return usuarioLogado.getId();
    }
}
