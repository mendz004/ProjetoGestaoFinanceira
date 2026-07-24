package br.com.ifba.gestaofinanceira.cartaoCredito.controller;

import br.com.ifba.gestaofinanceira.cartaoCredito.dto.CartaoGetDto;
import br.com.ifba.gestaofinanceira.cartaoCredito.dto.CartaoPostDto;
import br.com.ifba.gestaofinanceira.cartaoCredito.dto.PagamentoFaturaDto;
import br.com.ifba.gestaofinanceira.cartaoCredito.entity.Cartao;
import br.com.ifba.gestaofinanceira.cartaoCredito.service.CartaoService;
import br.com.ifba.gestaofinanceira.Infraestructure.mapper.ObjectMapperUtil;
import br.com.ifba.gestaofinanceira.usuario.entity.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    @Autowired
    private ObjectMapperUtil objectMapperUtil;

    @PostMapping
    public ResponseEntity<CartaoGetDto> cadastrarCartao(@RequestBody @Valid CartaoPostDto dto) {

        Long usuarioId = getUsuarioLogadoId();
        Cartao novoCartao = cartaoService.cadastrarCartao(dto, usuarioId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(objectMapperUtil.map(novoCartao, CartaoGetDto.class));
    }

    @GetMapping
    public ResponseEntity<List<CartaoGetDto>> listarTodos() {

        Long usuarioId = getUsuarioLogadoId();
        return ResponseEntity.status(HttpStatus.OK)
                .body(objectMapperUtil.mapAll(
                        cartaoService.findAll(usuarioId),
                        CartaoGetDto.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {

        Long usuarioId = getUsuarioLogadoId();
        cartaoService.deleteById(id, usuarioId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cartao> atualizar(@PathVariable Long id, @RequestBody Cartao cartao) {

        Long usuarioId = getUsuarioLogadoId();
        Cartao atualizarCartao = cartaoService.atualizar(id, cartao, usuarioId);
        return ResponseEntity.ok(atualizarCartao);
    }

    @PostMapping("/{id}/pagar-fatura")
    public ResponseEntity<?> pagarFatura(@PathVariable Long id, @RequestBody PagamentoFaturaDto dto) {
        cartaoService.pagarFatura(id, dto);
        return ResponseEntity.ok().body("Fatura paga com sucesso!");
    }

    private Long getUsuarioLogadoId() {
        // Extrai o usuário que o SecurityFilter validou a partir do JWT
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return usuarioLogado.getId();
    }
}
