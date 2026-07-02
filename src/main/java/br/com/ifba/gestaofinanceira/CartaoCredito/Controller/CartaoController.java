package br.com.ifba.gestaofinanceira.CartaoCredito.Controller;

import br.com.ifba.gestaofinanceira.CartaoCredito.Dto.CartaoGetDto;
import br.com.ifba.gestaofinanceira.CartaoCredito.Dto.CartaoPostDto;
import br.com.ifba.gestaofinanceira.CartaoCredito.Entity.Cartao;
import br.com.ifba.gestaofinanceira.CartaoCredito.Service.CartaoService;
import br.com.ifba.gestaofinanceira.Infraestructure.Mapper.ObjectMapperUtil;
import br.com.ifba.gestaofinanceira.Usuario.Entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    @Autowired
    private ObjectMapperUtil objectMapperUtil;

    @PostMapping("/cadastrar")
    public ResponseEntity<CartaoGetDto> cadastrarCartao(@RequestBody CartaoPostDto dto) {

        Cartao novoCartao = cartaoService.cadastrarCartao(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(objectMapperUtil.map(novoCartao, CartaoGetDto.class));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<CartaoGetDto>> listarTodos() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(objectMapperUtil.mapAll(
                        cartaoService.listarTodos(),
                        CartaoGetDto.class));
    }
}
