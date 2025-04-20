package com.punkbolos.punkbolos_app.dto;

import java.math.BigDecimal;

public class ReceitaCustoDTO {
    private String nome;
    private BigDecimal custoIngredientes;
    private BigDecimal taxasFixas;
    private BigDecimal subtotalComTaxas;
    private BigDecimal precoFinal;

    public ReceitaCustoDTO(String nome, BigDecimal custoIngredientes, BigDecimal taxasFixas, BigDecimal subtotalComTaxas, BigDecimal precoFinal) {
        this.nome = nome;
        this.custoIngredientes = custoIngredientes;
        this.taxasFixas = taxasFixas;
        this.subtotalComTaxas = subtotalComTaxas;
        this.precoFinal = precoFinal;
    }

	public String getNome() {
		return nome;
	}

	public BigDecimal getCustoIngredientes() {
		return custoIngredientes;
	}

	public BigDecimal getTaxasFixas() {
		return taxasFixas;
	}

	public BigDecimal getSubtotalComTaxas() {
		return subtotalComTaxas;
	}

	public BigDecimal getPrecoFinal() {
		return precoFinal;
	}  
}
