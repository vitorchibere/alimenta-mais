package com.alimentamais.service;

import com.alimentamais.model.Oferta;
import com.alimentamais.model.Solicitacao;
import com.alimentamais.model.StatusOferta;
import com.alimentamais.model.StatusSolicitacao;
import com.alimentamais.model.Usuario;
import com.alimentamais.repository.SolicitacaoRepository;
import com.alimentamais.repository.OfertaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolicitacaoService {

    private final SolicitacaoRepository solicitacaoRepository;
    private final OfertaRepository ofertaRepository;

    public SolicitacaoService(
            SolicitacaoRepository solicitacaoRepository,
            OfertaRepository ofertaRepository) {

        this.solicitacaoRepository = solicitacaoRepository;
        this.ofertaRepository = ofertaRepository;
    }

    public Solicitacao criar(Usuario beneficiario, Oferta oferta) {

        if (oferta.getStatus() != StatusOferta.DISPONIVEL) {
            throw new IllegalArgumentException(
                    "Não é possível solicitar uma oferta que não está disponível"
            );
        }

        Solicitacao solicitacao = new Solicitacao(beneficiario, oferta);

        return solicitacaoRepository.save(solicitacao);
    }

    public List<Solicitacao> listarSolicitadas() {
        return solicitacaoRepository.findByStatus(StatusSolicitacao.SOLICITADA);
    }

    public List<Solicitacao> listarTodas() {
        return solicitacaoRepository.findAll();
    }

    public Solicitacao atualizarStatus(String id, StatusSolicitacao novoStatus) {

        Solicitacao solicitacao = solicitacaoRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Solicitação não encontrada"));

        StatusSolicitacao statusAtual = solicitacao.getStatus();

        if (statusAtual == StatusSolicitacao.SOLICITADA
                && (novoStatus == StatusSolicitacao.ACEITA
                || novoStatus == StatusSolicitacao.RECUSADA)) {

            solicitacao.setStatus(novoStatus);

            if (novoStatus == StatusSolicitacao.ACEITA) {
                Oferta oferta = solicitacao.getOferta();
                oferta.setStatus(StatusOferta.RESERVADA);
                ofertaRepository.save(oferta);
            }

        } else if (statusAtual == StatusSolicitacao.ACEITA
                && novoStatus == StatusSolicitacao.CONCLUIDA) {

            solicitacao.setStatus(novoStatus);

        } else {
            throw new IllegalArgumentException(
                    "Transição de status não permitida"
            );
        }

        return solicitacaoRepository.save(solicitacao);
    }
}