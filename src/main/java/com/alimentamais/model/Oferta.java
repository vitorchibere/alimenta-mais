package com.alimentamais.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ofertas")
public class Oferta {

    @Id
    private String id;

    private String alimento;
    private double quantidade;
    private String unidade;
    private String descricao;
    private String cidade;
    private StatusOferta status;
    private Usuario doador;

    public Oferta() {
    }

    public Oferta(String alimento, double quantidade, String unidade,
                  String descricao, String cidade, Usuario doador) {
        this.alimento = alimento;
        this.quantidade = quantidade;
        this.unidade = unidade;
        this.descricao = descricao;
        this.cidade = cidade;
        this.doador = doador;
        this.status = StatusOferta.DISPONIVEL;
    }

    public String getId() {
        return id;
    }

    public String getAlimento() {
        return alimento;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public String getUnidade() {
        return unidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCidade() {
        return cidade;
    }

    public StatusOferta getStatus() {
        return status;
    }

    public Usuario getDoador() {
        return doador;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAlimento(String alimento) {
        this.alimento = alimento;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setStatus(StatusOferta status) {
        this.status = status;
    }

    public void setDoador(Usuario doador) {
        this.doador = doador;
    }
}