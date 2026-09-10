package com.alimentamais.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usuarios")
public class Usuario {

    @Id
    private String id;

    private String nome;
    private String cidade;
    private TipoUsuario tipoUsuario;

    public Usuario() {
    }

    public Usuario(String nome, String cidade, TipoUsuario tipoUsuario) {
        this.nome = nome;
        this.cidade = cidade;
        this.tipoUsuario = tipoUsuario;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCidade() {
        return cidade;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}