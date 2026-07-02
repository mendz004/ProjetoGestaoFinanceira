package br.com.ifba.gestaofinanceira.Despesa.Controller;

import br.com.ifba.gestaofinanceira.CartaoCredito.Dto.CartaoGetDto;
import br.com.ifba.gestaofinanceira.Conta.Dto.ContaGetDto;
import br.com.ifba.gestaofinanceira.Conta.Entity.Conta;
import br.com.ifba.gestaofinanceira.Despesa.Dto.DespesaGetDto;
import br.com.ifba.gestaofinanceira.Despesa.Dto.DespesaPostDto;
import br.com.ifba.gestaofinanceira.Despesa.Entity.Despesa;
import br.com.ifba.gestaofinanceira.Despesa.Service.DespesaService;
import br.com.ifba.gestaofinanceira.Infraestructure.Mapper.ObjectMapperUtil;
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
    public ResponseEntity<DespesaGetDto> registrarDespesa(@RequestBody DespesaPostDto despesaPostDto) {

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
        try {
            return ResponseEntity.ok(despesaService.buscarPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirDespesa(@PathVariable Long id) {
        try {
            despesaService.excluirDespesa(id);
            return ResponseEntity.ok("Despesa excluída e saldo atualizado com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
