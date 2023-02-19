package com.gestaoensino.gestao_ensino.domain.wrappers.aws.s3.models;

import com.amazonaws.services.s3.model.Bucket;

import java.util.Date;

public class AwsBucket {

    private String nome;
    private String proprietario;
    private Date dataCriacao;

    public AwsBucket() {}

    public AwsBucket(String nome) {
        this.nome = nome;
    }

    public AwsBucket(Bucket bucket) {
        nome = bucket.getName();
        proprietario = bucket.getOwner().getDisplayName();
        dataCriacao = bucket.getCreationDate();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
