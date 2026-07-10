package br.com.ifba.gestaofinanceira.relatorio.controller;

import br.com.ifba.gestaofinanceira.Infraestructure.mapper.ObjectMapperUtil;
import br.com.ifba.gestaofinanceira.relatorio.dto.RelatorioGetDto;
import br.com.ifba.gestaofinanceira.relatorio.dto.RelatorioPostDto;
import br.com.ifba.gestaofinanceira.relatorio.entity.RelatorioMensal;
import br.com.ifba.gestaofinanceira.relatorio.service.RelatorioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @Autowired
    private ObjectMapperUtil objectMapperUtil;

    @PostMapping("/gerar")
    public ResponseEntity<RelatorioGetDto> gerarRelatorio(@RequestBody @Valid RelatorioPostDto dto) {
        RelatorioMensal novoRelatorio = relatorioService.gerarRelatorio(dto);

        RelatorioGetDto resposta = objectMapperUtil.map(novoRelatorio, RelatorioGetDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);

    }

    @GetMapping("/listar")
    public ResponseEntity<List<RelatorioGetDto>> findAll() {

        return ResponseEntity.status(HttpStatus.OK)
                .body(objectMapperUtil.mapAll(this.relatorioService.findAll(), RelatorioGetDto.class));
    }
}
