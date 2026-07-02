package br.com.ifba.gestaofinanceira.CartaoCredito.Service;

import br.com.ifba.gestaofinanceira.CartaoCredito.Dto.CartaoPostDto;
import br.com.ifba.gestaofinanceira.CartaoCredito.Entity.Cartao;
import br.com.ifba.gestaofinanceira.CartaoCredito.Repository.CartaoRepository;
import br.com.ifba.gestaofinanceira.Usuario.Entity.Usuario;
import br.com.ifba.gestaofinanceira.Usuario.Repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartaoService implements CartaoIService {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    @Override
    public Cartao cadastrarCartao(CartaoPostDto dto) {

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Cartao cartao = new Cartao();
        cartao.setNome(dto.getNome());
        cartao.setLimiteTotal(dto.getLimiteTotal());
        cartao.setLimiteDisponivel(dto.getLimiteTotal());
        cartao.setDiaFechamento(dto.getDiaFechamento());
        cartao.setDiaVencimento(dto.getDiaFechamento());
        cartao.setUsuario(usuario);

        return cartaoRepository.save(cartao);
    }

    @Override
    public List<Cartao> listarTodos() {
        return cartaoRepository.findAll();
    }

}
