package com.pricefriend.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pricefriend.api.model.Receita;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {}
