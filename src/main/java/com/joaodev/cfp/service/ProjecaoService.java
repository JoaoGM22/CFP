package com.joaodev.cfp.service;

import com.joaodev.cfp.DTO.ProjecaoDTO;
import com.joaodev.cfp.DTO.ProjecaoDetalhadaDTO;
import com.joaodev.cfp.DTO.TransacoesDTO;
import com.joaodev.cfp.entity.Contas;
import com.joaodev.cfp.entity.Transacoes;
import com.joaodev.cfp.entity.Usuario;
import com.joaodev.cfp.repository.ContasRepository;
import com.joaodev.cfp.repository.TransacoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjecaoService {

    @Autowired
    private TransacoesRepository transacaoRepository;

    @Autowired
    private ContasRepository contaRepository;

    @Autowired
    private UsuarioService usuarioService;

    public List<ProjecaoDTO> calcularProjecaoDiaria(LocalDate dataInicio, LocalDate dataFim, Authentication authentication) {
        String email = authentication.getName();
        Optional<Usuario> usuario = usuarioService.findByEmail(email);

        if (dataInicio.isAfter(dataFim)) {
            throw new IllegalArgumentException("A data início não pode ser posterior à data fim");
        }

        BigDecimal saldoAtual = usuario.get().getFinanceiro().getSaldo();

        List<Transacoes> transacoesFuturas = criarTransacoesComAsParcelas(transacaoRepository.findByDataBetweenAndUsuarioId(
                dataInicio, dataFim, usuario.get().getId()));

        transacoesFuturas.addAll(contasParaTransacoes(usuario.get()));

        List<Transacoes> transacoesComContasRepetidas = new ArrayList<>();
        for (Transacoes transacao : transacoesFuturas) {
            if (transacao.getCategoria().equals("Conta") || transacao.getFixo()) {
                LocalDate dataTransacao = transacao.getData();
                while (!dataTransacao.isAfter(dataFim)) {
                    Transacoes transacaoRepetida = new Transacoes();
                    transacaoRepetida.setDescricao(transacao.getDescricao());
                    transacaoRepetida.setValor(transacao.getValor());
                    transacaoRepetida.setTipo(transacao.getTipo());
                    transacaoRepetida.setCategoria(transacao.getCategoria());
                    transacaoRepetida.setData(dataTransacao);
                    transacoesComContasRepetidas.add(transacaoRepetida);

                    dataTransacao = dataTransacao.plusMonths(1);
                }
            } else {
                transacoesComContasRepetidas.add(transacao);
            }
        }
        transacoesComContasRepetidas.addAll(listaTransacoesSalario(dataFim,usuario.get()));
        transacoesFuturas = transacoesComContasRepetidas;

        Map<LocalDate, BigDecimal> projecoesMap = new TreeMap<>();

        LocalDate dataAtual = dataInicio;
        BigDecimal saldoProjetado = saldoAtual;

        while (!dataAtual.isAfter(dataFim)) {
            // Criar uma variável efetivamente final para o uso na lambda
            final LocalDate dataFiltro = dataAtual;

            List<Transacoes> transacoesDoDia = transacoesFuturas.stream()
                    .filter(t -> t.getData().isEqual(dataFiltro))
                    .collect(Collectors.toList());

            for (Transacoes transacao : transacoesDoDia) {
                if ("entrada".equals(transacao.getTipo())) {
                    saldoProjetado = saldoProjetado.add(transacao.getValor());
                } else if ("saida".equals(transacao.getTipo())) {
                    saldoProjetado = saldoProjetado.subtract(transacao.getValor());
                }
            }

            projecoesMap.put(dataAtual, saldoProjetado);

            dataAtual = dataAtual.plusDays(1);
        }

        List<ProjecaoDTO> resultado = new ArrayList<>();

        for (Map.Entry<LocalDate, BigDecimal> entry : projecoesMap.entrySet()) {
            ProjecaoDTO dto = new ProjecaoDTO();
            dto.setData(entry.getKey().toString());
            dto.setSaldo(entry.getValue());
            resultado.add(dto);
        }

        return resultado;
    }


    public List<ProjecaoDetalhadaDTO> calcularProjecaoDiariaDetalhada(LocalDate dataInicio, LocalDate dataFim, Authentication authentication) {
        String email = authentication.getName();
        Optional<Usuario> usuario = usuarioService.findByEmail(email);

        if (dataInicio.isAfter(dataFim)) {
            throw new IllegalArgumentException("A data início não pode ser posterior à data fim");
        }

        BigDecimal saldoAtual = usuario.get().getFinanceiro().getSaldo();

        List<Transacoes> transacoesFuturas = criarTransacoesComAsParcelas(transacaoRepository.findByDataBetweenAndUsuarioId(
                dataInicio, dataFim, usuario.get().getId()));

        transacoesFuturas.addAll(contasParaTransacoes(usuario.get()));

        List<Transacoes> transacoesComContasRepetidas = new ArrayList<>();
        for (Transacoes transacao : transacoesFuturas) {
            if (transacao.getCategoria().equals("Conta") || transacao.getFixo()) {
                LocalDate dataTransacao = transacao.getData();
                while (!dataTransacao.isAfter(dataFim)) {
                    Transacoes transacaoRepetida = new Transacoes();
                    transacaoRepetida.setDescricao(transacao.getDescricao());
                    transacaoRepetida.setValor(transacao.getValor());
                    transacaoRepetida.setTipo(transacao.getTipo());
                    transacaoRepetida.setCategoria(transacao.getCategoria());
                    transacaoRepetida.setData(dataTransacao);
                    transacoesComContasRepetidas.add(transacaoRepetida);

                    dataTransacao = dataTransacao.plusMonths(1);
                }
            } else {
                transacoesComContasRepetidas.add(transacao);
            }
        }
        transacoesComContasRepetidas.addAll(listaTransacoesSalario(dataFim,usuario.get()));
        transacoesFuturas = transacoesComContasRepetidas;

        Map<LocalDate, ProjecaoDetalhadaDTO> projecoesMap = new TreeMap<>();

        LocalDate dataAtual = dataInicio;
        while (!dataAtual.isAfter(dataFim)) {
            ProjecaoDetalhadaDTO dto = new ProjecaoDetalhadaDTO();
            dto.setData(dataAtual.toString());
            dto.setTransacoes(new ArrayList<>());
            projecoesMap.put(dataAtual, dto);

            dataAtual = dataAtual.plusDays(1);
        }

        Map<LocalDate, List<Transacoes>> transacoesPorDia = transacoesFuturas.stream()
                .collect(Collectors.groupingBy(Transacoes::getData));

        for (Map.Entry<LocalDate, List<Transacoes>> entry : transacoesPorDia.entrySet()) {
            LocalDate data = entry.getKey();
            List<Transacoes> transacoesDoDia = entry.getValue();

            if (projecoesMap.containsKey(data)) {
                ProjecaoDetalhadaDTO dto = projecoesMap.get(data);

                for (Transacoes transacao : transacoesDoDia) {
                    TransacoesDTO transacaoDTO = new TransacoesDTO();
                    transacaoDTO.setId(transacao.getId());
                    transacaoDTO.setDescricao(transacao.getDescricao());
                    transacaoDTO.setValor(transacao.getValor());
                    transacaoDTO.setTipo(transacao.getTipo());

                    dto.getTransacoes().add(transacaoDTO);
                }
            }
        }

        BigDecimal saldoProjetado = saldoAtual;
        for (LocalDate data = dataInicio; !data.isAfter(dataFim); data = data.plusDays(1)) {
            ProjecaoDetalhadaDTO dto = projecoesMap.get(data);

            for (TransacoesDTO transacao : dto.getTransacoes()) {
                if ("entrada".equals(transacao.getTipo())) {
                    saldoProjetado = saldoProjetado.add(transacao.getValor());
                } else if ("saida".equals(transacao.getTipo())) {
                    saldoProjetado = saldoProjetado.subtract(transacao.getValor());
                }
            }

            dto.setSaldo(saldoProjetado);
        }

        return new ArrayList<>(projecoesMap.values());
    }

    private List<Transacoes> listaTransacoesSalario(LocalDate dataFim,Usuario usuario) {
        List<Transacoes> lista = new ArrayList<>();
        LocalDate dataAtual = LocalDate.now().withDayOfMonth(1);

        while (!dataAtual.isAfter(dataFim)) {
            int contador = 0;
            LocalDate data = dataAtual;

            while (data.getMonth() == dataAtual.getMonth()) {
                if (data.getDayOfWeek() != DayOfWeek.SATURDAY && data.getDayOfWeek() != DayOfWeek.SUNDAY) {
                    contador++;
                    if (contador == 5) {
                        Transacoes transacaoSalario = new Transacoes();
                        transacaoSalario.setData(data);
                        transacaoSalario.setTipo("entrada");
                        transacaoSalario.setCategoria("Salário");
                        transacaoSalario.setDescricao("Salário");
                        transacaoSalario.setUsuario(usuario);
                        transacaoSalario.setValor(usuario.getFinanceiro().getSalario());
                        transacaoSalario.setParcelas(0);
                        transacaoSalario.setFixo(true);
                        lista.add(transacaoSalario);
                        break;
                    }
                }
                data = data.plusDays(1);
            }
            dataAtual = dataAtual.plusMonths(1).withDayOfMonth(1);
        }

        return lista;
    }

    private List<Transacoes> contasParaTransacoes(Usuario usuario){
        List<Contas> contas = contaRepository.findByUsuarioId(usuario.getId());
        List<Transacoes> lista = new ArrayList<>();

        for(Contas conta : contas){
            Transacoes transacoes = new Transacoes();

            transacoes.setData(conta.getData());
            transacoes.setTipo("saida");
            transacoes.setCategoria("Conta");
            transacoes.setDescricao(conta.getDescricao());
            transacoes.setUsuario(usuario);
            transacoes.setValor(conta.getValor());
            transacoes.setParcelas(0);
            transacoes.setFixo(true);
            lista.add(transacoes);
        }

        return lista;
    }

    private List<Transacoes>criarTransacoesComAsParcelas(List<Transacoes> listaTransacoes){
        List<Transacoes> listaComParcelas = new ArrayList<>();
        for(Transacoes transacoes : listaTransacoes){
            if (transacoes.getParcelas() != 0){
                int contagem = 0;
                int parcela = 1;
                BigDecimal valor = transacoes.getValor().divide(BigDecimal.valueOf(transacoes.getParcelas()), 2, RoundingMode.HALF_UP);

                while(transacoes.getParcelas() > contagem){
                    Transacoes transacao = new Transacoes();
                    transacao.setDescricao(transacoes.getDescricao() + "(" + parcela + "/" + transacoes.getParcelas() + ")");
                    transacao.setFixo(false);
                    transacao.setParcelas(0);
                    transacao.setValor(valor);
                    transacao.setTipo(transacoes.getTipo());
                    transacao.setUsuario(transacoes.getUsuario());
                    transacao.setCategoria(transacoes.getCategoria());
                    transacao.setData(transacoes.getData().plusMonths(contagem));
                    listaComParcelas.add(transacao);
                    parcela++;
                    contagem++;
                }
            }else{
                listaComParcelas.add(transacoes);
            }

        }
        return listaComParcelas;
    }


}
