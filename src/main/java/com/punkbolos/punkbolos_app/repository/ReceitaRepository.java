package com.punkbolos.punkbolos_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punkbolos.punkbolos_app.model.Receita;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {}
