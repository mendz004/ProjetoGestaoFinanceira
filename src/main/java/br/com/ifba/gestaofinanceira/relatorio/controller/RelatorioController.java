package br.com.ifba.gestaofinanceira.relatorio.controller;

import br.com.ifba.gestaofinanceira.Infraestructure.mapper.ObjectMapperUtil;
import br.com.ifba.gestaofinanceira.relatorio.dto.RelatorioGetDto;
import br.com.ifba.gestaofinanceira.relatorio.dto.RelatorioMensalDto;
import br.com.ifba.gestaofinanceira.relatorio.service.RelatorioService;
import br.com.ifba.gestaofinanceira.usuario.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @Autowired
    private ObjectMapperUtil objectMapperUtil;

    @GetMapping("/mensal/conta/{contaId}")
    public ResponseEntity<RelatorioMensalDto> gerarRelatorioMensal(
            @PathVariable Long contaId,
            @RequestParam(required = false) Integer mes,
            @RequestParam(required = false) Integer ano) {

        Long usuarioId = getUsuarioLogadoId();

        if (mes == null || ano == null) {
            LocalDate hoje = LocalDate.now();
            mes = hoje.getMonthValue();
            ano = hoje.getYear();
        }

        RelatorioMensalDto relatorio = relatorioService.gerarRelatorioMensal(contaId, mes, ano, usuarioId);

        return ResponseEntity.ok(relatorio);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<RelatorioGetDto>> findAll() {

        Long usuarioId = getUsuarioLogadoId();

        return ResponseEntity.status(HttpStatus.OK)
                .body(objectMapperUtil.mapAll(this.relatorioService.findAllByUsuarioId(usuarioId), RelatorioGetDto.class));
    }

    private Long getUsuarioLogadoId() {
        // Extrai o usuário que o SecurityFilter validou a partir do JWT
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return usuarioLogado.getId();
    }
}