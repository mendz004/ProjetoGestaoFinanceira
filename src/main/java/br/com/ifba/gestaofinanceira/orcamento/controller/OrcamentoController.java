package br.com.ifba.gestaofinanceira.orcamento.controller;

import br.com.ifba.gestaofinanceira.Infraestructure.mapper.ObjectMapperUtil;
import br.com.ifba.gestaofinanceira.orcamento.dto.OrcamentoGetDto;
import br.com.ifba.gestaofinanceira.orcamento.dto.OrcamentoPostDto;
import br.com.ifba.gestaofinanceira.orcamento.entity.Orcamento;
import br.com.ifba.gestaofinanceira.orcamento.enun.CategoriaDespesa;
import br.com.ifba.gestaofinanceira.orcamento.service.OrcamentoIService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/cadastrar")
    public ResponseEntity<OrcamentoGetDto> cadastrar(@RequestBody @Valid OrcamentoPostDto dto) {
        Orcamento novoOrcamento = orcamentoService.cadastrarOrcamento(dto);

        OrcamentoGetDto resposta = objectMapperUtil.map(novoOrcamento, OrcamentoGetDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<OrcamentoGetDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(
                objectMapperUtil.mapAll(orcamentoService.findAll(), OrcamentoGetDto.class)
        );
    }

    @GetMapping("/sugestao/{usuarioId}")
    public ResponseEntity<Map<CategoriaDespesa, Double>> sugerir(@PathVariable Long usuarioId) {
        Map<CategoriaDespesa, Double> sugestao = orcamentoService.gerarSugestaoOrcamento(usuarioId);
        return ResponseEntity.status(HttpStatus.OK).body(sugestao);
    }

}
