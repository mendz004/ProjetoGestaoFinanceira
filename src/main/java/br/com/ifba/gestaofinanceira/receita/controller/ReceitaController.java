package br.com.ifba.gestaofinanceira.receita.controller;

import br.com.ifba.gestaofinanceira.Infraestructure.mapper.ObjectMapperUtil;
import br.com.ifba.gestaofinanceira.receita.dto.ReceitaGetDto;
import br.com.ifba.gestaofinanceira.receita.dto.ReceitaPostDto;
import br.com.ifba.gestaofinanceira.receita.entity.Receita;
import br.com.ifba.gestaofinanceira.receita.service.ReceitaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {

    @Autowired
    private ReceitaService receitaService;

    @Autowired
    private ObjectMapperUtil objectMapperUtil;

    @PostMapping("/cadastrar")
    public ResponseEntity<ReceitaGetDto> cadastrarReceita(@RequestBody @Valid ReceitaPostDto receitaPostDto) {
        Receita novaReceita = receitaService.cadastrarReceita(receitaPostDto);

        ReceitaGetDto resposta = objectMapperUtil.map(novaReceita, ReceitaGetDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ReceitaGetDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(objectMapperUtil.mapAll
                (this.receitaService.findAll(),
                        ReceitaGetDto.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirDespesa(@PathVariable Long id) {

        receitaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Receita> atualizar(@PathVariable Long id, @RequestBody Receita receita) {

        Receita receitaAtualizada = receitaService.atualizar(id, receita);
        return ResponseEntity.ok(receitaAtualizada);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Receita>> buscar(@RequestParam String termo) {

        List<Receita> receitas = receitaService.findByDescricao(termo);
        return ResponseEntity.ok(receitas);
    }
}
