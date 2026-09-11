package com.alimentamais.service;

import com.alimentamais.model.Oferta;
import com.alimentamais.model.Solicitacao;
import com.alimentamais.model.StatusOferta;
import com.alimentamais.model.StatusSolicitacao;
import com.alimentamais.model.Usuario;
import com.alimentamais.repository.SolicitacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolicitacaoService {

    private final SolicitacaoRepository solicitacaoRepository;

    public SolicitacaoService(SolicitacaoRepository solicitacaoRepository) {
        this.solicitacaoRepository = solicitacaoRepository;
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

    public Solicitacao atualizarStatus(String id, StatusSolicitacao status) {
        Solicitacao solicitacao = solicitacaoRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Solicitação não encontrada"));

        solicitacao.setStatus(status);

        return solicitacaoRepository.save(solicitacao);
    }
}