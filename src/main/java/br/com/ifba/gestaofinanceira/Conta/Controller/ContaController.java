package br.com.ifba.gestaofinanceira.Conta.Controller;

import br.com.ifba.gestaofinanceira.Conta.Dto.ContaGetDto;
import br.com.ifba.gestaofinanceira.Conta.Dto.ContaPostDto;
import br.com.ifba.gestaofinanceira.Conta.Entity.Conta;
import br.com.ifba.gestaofinanceira.Conta.Service.ContaService;
import br.com.ifba.gestaofinanceira.Infraestructure.Mapper.ObjectMapperUtil;
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
    public ResponseEntity<List<ContaGetDto>> listar() {
        return ResponseEntity.status(HttpStatus.OK).body(objectMapperUtil.mapAll
                (this.contaService.listarTodas(),
                        ContaGetDto.class));
    }


}
