package br.com.ifba.gestaofinanceira.alerta.service;

import br.com.ifba.gestaofinanceira.alerta.dto.AlertaGetDto;

import java.util.List;

public interface AlertaIService {

    List<AlertaGetDto> buscarAlertasDoUsuario(Long usuarioId, Integer mes, Integer ano);
}
