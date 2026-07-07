package br.com.ifba.gestaofinanceira.despesa.controller;

import br.com.ifba.gestaofinanceira.despesa.dto.DespesaGetDto;
import br.com.ifba.gestaofinanceira.despesa.dto.DespesaPostDto;
import br.com.ifba.gestaofinanceira.despesa.entity.Despesa;
import br.com.ifba.gestaofinanceira.despesa.service.DespesaService;
import br.com.ifba.gestaofinanceira.Infraestructure.mapper.ObjectMapperUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/despesas")
public class DespesaController {

    @Autowired
    private DespesaService despesaService;

    @Autowired
    private ObjectMapperUtil objectMapperUtil;


    @PostMapping("/registrar")
    public ResponseEntity<DespesaGetDto> registrarDespesa(@RequestBody @Valid DespesaPostDto despesaPostDto) {

        Despesa despesa = despesaService.registrarDespesa(despesaPostDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(objectMapperUtil.map(despesa, DespesaGetDto.class));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<DespesaGetDto>> listarTodas() {
        return ResponseEntity.status(HttpStatus.OK).body(objectMapperUtil.mapAll
                (this.despesaService.listarTodas(),
                        DespesaGetDto.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
            return ResponseEntity.ok(despesaService.buscarPorId(id));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirDespesa(@PathVariable Long id) {

        despesaService.excluirDespesa(id);

        return ResponseEntity.noContent().build();
    }

}
