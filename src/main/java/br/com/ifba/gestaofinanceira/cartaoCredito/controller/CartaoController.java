package br.com.ifba.gestaofinanceira.cartaoCredito.controller;

import br.com.ifba.gestaofinanceira.cartaoCredito.dto.CartaoGetDto;
import br.com.ifba.gestaofinanceira.cartaoCredito.dto.CartaoPostDto;
import br.com.ifba.gestaofinanceira.cartaoCredito.entity.Cartao;
import br.com.ifba.gestaofinanceira.cartaoCredito.service.CartaoService;
import br.com.ifba.gestaofinanceira.Infraestructure.mapper.ObjectMapperUtil;
import jakarta.validation.Valid;
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
    public ResponseEntity<CartaoGetDto> cadastrarCartao(@RequestBody @Valid CartaoPostDto dto) {

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
