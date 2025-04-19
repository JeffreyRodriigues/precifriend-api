package com.punkbolos.punkbolos_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punkbolos.punkbolos_app.model.Ingrediente;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {
}
