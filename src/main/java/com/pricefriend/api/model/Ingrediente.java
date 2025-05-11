package com.pricefriend.api.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Integer quantidadeEmbalagem; // em gramas
    private BigDecimal custoEmbalagem;

    // Construtor vazio (obrigatório para o JPA)
    public Ingrediente() {
    }

    // Construtor com argumentos (opcional, mas útil)
    public Ingrediente(String nome, Integer quantidadeEmbalagem, BigDecimal custoEmbalagem) {
        this.nome = nome;
        this.quantidadeEmbalagem = quantidadeEmbalagem;
        this.custoEmbalagem = custoEmbalagem;
    }

    // Getters e Setters
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

    public Integer getQuantidadeEmbalagem() {
        return quantidadeEmbalagem;
    }

    public void setQuantidadeEmbalagem(Integer quantidadeEmbalagem) {
        this.quantidadeEmbalagem = quantidadeEmbalagem;
    }

    public BigDecimal getCustoEmbalagem() {
        return custoEmbalagem;
    }

    public void setCustoEmbalagem(BigDecimal custoEmbalagem) {
        this.custoEmbalagem = custoEmbalagem;
    }

    // toString (opcional, mas útil para debugar)
    @Override
    public String toString() {
        return "Ingrediente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", quantidadeEmbalagem=" + quantidadeEmbalagem +
                ", custoEmbalagem=" + custoEmbalagem +
                '}';
    }
}
