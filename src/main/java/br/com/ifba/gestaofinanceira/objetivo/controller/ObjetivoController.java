package br.com.ifba.gestaofinanceira.objetivo.controller;

import br.com.ifba.gestaofinanceira.Infraestructure.mapper.ObjectMapperUtil;
import br.com.ifba.gestaofinanceira.objetivo.dto.ObjetivoGetDto;
import br.com.ifba.gestaofinanceira.objetivo.dto.ObjetivoPostDto;
import br.com.ifba.gestaofinanceira.objetivo.entity.ObjetivoFinanceiro;
import br.com.ifba.gestaofinanceira.objetivo.service.ObjetivoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/objetivos")
public class ObjetivoController {

    @Autowired
    private ObjetivoService service;

    @Autowired
    private ObjectMapperUtil objectMapperUtil;

    @PostMapping("/cadastrar")
    public ResponseEntity<ObjetivoGetDto> cadastrar(@RequestBody @Valid ObjetivoPostDto dto) {
        ObjetivoFinanceiro novoObjetivo = service.cadastrarObjetivo(dto);

        ObjetivoGetDto resposta = objectMapperUtil.map(novoObjetivo, ObjetivoGetDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ObjetivoGetDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(
                objectMapperUtil.mapAll(service.findAll(), ObjetivoGetDto.class)
        );
    }

    @PutMapping("/{id}/depositar")
    public ResponseEntity<ObjetivoGetDto> depositar(@PathVariable Long id, @RequestParam Double valor) {
        ObjetivoFinanceiro objetivoAtualizado = service.depositar(id, valor);

        ObjetivoGetDto resposta = objectMapperUtil.map(objetivoAtualizado, ObjetivoGetDto.class);

        return ResponseEntity.status(HttpStatus.OK).body(resposta);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ObjetivoFinanceiro> atualizar(@PathVariable Long id, @RequestBody ObjetivoFinanceiro objetivo) {

        ObjetivoFinanceiro objetivoAtualizado = service.atualizar(id, objetivo);
        return ResponseEntity.ok(objetivoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {

        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
