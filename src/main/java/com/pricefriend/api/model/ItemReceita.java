package com.pricefriend.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ItemReceita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ingrediente_id")
    private Ingrediente ingrediente;

    private Integer quantidade; // em gramas (ou mL)

    @ManyToOne
    @JoinColumn(name = "receita_id")
    @JsonBackReference
    private Receita receita;

    // Getters e Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Ingrediente getIngrediente() { return ingrediente; }

    public void setIngrediente(Ingrediente ingrediente) { this.ingrediente = ingrediente; }

    public Integer getQuantidade() { return quantidade; }

    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

    public Receita getReceita() { return receita; }

    public void setReceita(Receita receita) { this.receita = receita; }
}
