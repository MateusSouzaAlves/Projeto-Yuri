package model;

import java.util.Objects;

public class Avaliacao {

    private int idAvaliacao;
    private int idProduto;
    private String nomeUsuario;
    private String comentario;
    private int nota;
    private String nomeProduto;

    public Avaliacao() {
    }

    public Avaliacao(int idProduto, String nomeUsuario, String comentario, int nota) {
        this.idProduto = idProduto;
        this.nomeUsuario = nomeUsuario;
        this.comentario = comentario;
        this.nota = nota;
    }

    public int getIdAvaliacao() {
        return idAvaliacao;
    }

    public void setIdAvaliacao(int idAvaliacao) {
        this.idAvaliacao = idAvaliacao;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    @Override
    public String toString() {
        return "Avaliacao{" +
                "idAvaliacao=" + idAvaliacao +
                ", idProduto=" + idProduto +
                ", nomeUsuario='" + nomeUsuario + '\'' +
                ", comentario='" + comentario + '\'' +
                ", nota=" + nota +
                ", nomeProduto='" + nomeProduto + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Avaliacao)) return false;
        Avaliacao that = (Avaliacao) o;
        return idAvaliacao == that.idAvaliacao;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAvaliacao);
    }
}
