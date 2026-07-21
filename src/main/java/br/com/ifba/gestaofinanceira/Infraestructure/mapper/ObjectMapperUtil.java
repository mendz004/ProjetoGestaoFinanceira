package br.com.ifba.gestaofinanceira.Infraestructure.mapper;

import br.com.ifba.gestaofinanceira.conta.entity.Conta;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.config.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ObjectMapperUtil {

    private static final ModelMapper MODEL_MAPPER;

    static {
        MODEL_MAPPER = new ModelMapper();

        MODEL_MAPPER.getConfiguration()
                .setAmbiguityIgnored(true)
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);

        Converter<Conta, String> contaParaString = context -> {
            Conta conta = context.getSource();
            return conta == null ? null : conta.getNomeConta();
        };

        MODEL_MAPPER.addConverter(contaParaString);
    }

    /*Esse metodo fica responsavel por converter um objeto de entrada
     * em outro tipo de objeto
     */
    public <Input, Output> Output map(final Input obect, final Class<Output> clazz) {

        return MODEL_MAPPER.map(obect, clazz);
    }

    /* Metodo responsavel por converter
     * Uma lista de objetos
     */
    public <Input, Output> List<Output> mapAll(
            final List<Input> objectList,
            final Class<Output> clazz) {

        return objectList.stream()
                .map(object -> map(object, clazz))
                .toList();
    }
}
