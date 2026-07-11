package br.com.ifba.gestaofinanceira.alerta.dto;

import br.com.ifba.gestaofinanceira.alerta.enun.NivelAlerta;
import br.com.ifba.gestaofinanceira.alerta.enun.TipoAlerta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlertaGetDto {

    private TipoAlerta tipo;
    private NivelAlerta nivel;
    private String mensagem;

}
