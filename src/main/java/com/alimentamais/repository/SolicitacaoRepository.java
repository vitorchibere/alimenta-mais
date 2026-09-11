package com.alimentamais.repository;

import com.alimentamais.model.Solicitacao;
import com.alimentamais.model.StatusSolicitacao;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SolicitacaoRepository extends MongoRepository<Solicitacao, String> {

    List<Solicitacao> findByStatus(StatusSolicitacao status);
}