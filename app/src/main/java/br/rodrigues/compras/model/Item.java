package br.rodrigues.compras.model;

import java.io.Serializable;

public class Item implements Serializable {

    private Long id;
    private String nome;
    private Double preco;
    private Double precoTotal;
    private String observacao;
    private int categoria;
    private Boolean comprado;
    private Long dataCompra;
    private int quantidade;
    private int caminhoImagem;

    public Double getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(Double precoTotal) {
        this.precoTotal = precoTotal;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Long getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Long dataCompra) {
        this.dataCompra = dataCompra;
    }

    public int getCaminhoImagem() {
        return caminhoImagem;
    }

    public void setCaminhoImagem(int caminhoImagem) {
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

}
