package com.alimentamais.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "solicitacoes")
public class Solicitacao {

    @Id
    private String id;

    private Usuario beneficiario;
    private Oferta oferta;
    private StatusSolicitacao status;

    public Solicitacao() {
    }

    public Solicitacao(Usuario beneficiario, Oferta oferta) {
        this.beneficiario = beneficiario;
        this.oferta = oferta;
        this.status = StatusSolicitacao.SOLICITADA;
    }

    public String getId() {
        return id;
    }

    public Usuario getBeneficiario() {
        return beneficiario;
    }

    public Oferta getOferta() {
        return oferta;
    }

    public StatusSolicitacao getStatus() {
        return status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBeneficiario(Usuario beneficiario) {
        this.beneficiario = beneficiario;
    }

    public void setOferta(Oferta oferta) {
        this.oferta = oferta;
    }

    public void setStatus(StatusSolicitacao status) {
        this.status = status;
    }
}