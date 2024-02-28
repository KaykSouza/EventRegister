package br.com.Cadastro.modelos;

import java.io.Serializable;

public class Pessoa implements Serializable {
    private String nome;
    private String cidade;
    private String email;
    private String senha;
    private boolean status = false;

    public Pessoa(String nome, String cidade, String email, String senha) {
        this.nome = nome;
        this.cidade = cidade;
        this.email = email;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
