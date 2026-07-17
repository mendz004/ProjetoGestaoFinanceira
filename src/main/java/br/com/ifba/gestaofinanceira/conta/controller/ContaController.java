package br.com.ifba.gestaofinanceira.conta.controller;

import br.com.ifba.gestaofinanceira.conta.dto.ContaGetDto;
import br.com.ifba.gestaofinanceira.conta.dto.ContaPostDto;
import br.com.ifba.gestaofinanceira.conta.entity.Conta;
import br.com.ifba.gestaofinanceira.conta.service.ContaService;
import br.com.ifba.gestaofinanceira.Infraestructure.mapper.ObjectMapperUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @Autowired
    private ObjectMapperUtil objectMapperUtil;

    @PostMapping("/cadastrar")
    public ResponseEntity<ContaGetDto> cadastrarConta(@RequestBody @Valid ContaPostDto dto) {

        Conta novaConta = contaService.cadastrarConta(dto);

        ContaGetDto resposta = objectMapperUtil.map(novaConta, ContaGetDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ContaGetDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(objectMapperUtil.mapAll
                (this.contaService.findAll(),
                        ContaGetDto.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {

        contaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Conta> atualizar(@PathVariable Long id, @RequestBody Conta conta) {
        Conta contaAtualizada = contaService.atualizar(id, conta);
        return ResponseEntity.ok(contaAtualizada);
    }

}
