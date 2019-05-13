package br.rodrigues.compras.model;

import java.io.Serializable;

public class Item implements Serializable {

    private Long id;
    private String nome;
    private Double preco;
    private String observacao;
    private int categoria;
    private Boolean comprado;
    private Long dataCompra;

    private String caminhoImagem;

    public Long getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Long dataCompra) {
        this.dataCompra = dataCompra;
    }

    public String getCaminhoImagem() {
        return caminhoImagem;
    }

    public void setCaminhoImagem(String caminhoImagem) {
        this.caminhoImagem = caminhoImagem;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public Boolean getComprado() {
        return comprado;
    }

    public void setComprado(Boolean comprado) {
        this.comprado = comprado;
    }

    @Override
    public String toString() {
        String show = getNome() + " - R$" + getPreco().toString() + " - " + getComprado();
        return show;
    }
}
