package br.com.ifba.gestaofinanceira.alerta.controller;

import br.com.ifba.gestaofinanceira.alerta.dto.AlertaGetDto;
import br.com.ifba.gestaofinanceira.alerta.service.AlertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/alertas")
public class AlertaController {

    @Autowired
    private AlertaService alertaService;

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<AlertaGetDto>> listarAlertas(@PathVariable Long usuarioId) {
        // Pega o mês e ano atual automaticamente para gerar os alertas do momento
        LocalDate hoje = LocalDate.now();

        List<AlertaGetDto> alertas = alertaService.buscarAlertasDoUsuario(usuarioId, hoje.getMonthValue(), hoje.getYear());

        return ResponseEntity.status(HttpStatus.OK).body(alertas);
    }
}
