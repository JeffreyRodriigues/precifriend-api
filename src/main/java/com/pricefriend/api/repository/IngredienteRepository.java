package com.pricefriend.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pricefriend.api.model.Ingrediente;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {
    // Verifica se já existe um ingrediente com o mesmo nome, ignorando maiúsculas/minúsculas
    Optional<Ingrediente> findByNomeIgnoreCase(String nome);
}
