package com.pricefriend.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pricefriend.api.model.Ingrediente;
import com.pricefriend.api.service.IngredienteService;

@RestController
@RequestMapping("/api/ingredientes")
public class IngredienteController {

    @Autowired
    private IngredienteService ingredienteService;

    @PostMapping
    public ResponseEntity<?> adicionarIngrediente(@RequestBody Ingrediente ingrediente) {
        try {
            Ingrediente salvo = ingredienteService.salvar(ingrediente);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<Ingrediente> listarIngredientes() {
        return ingredienteService.listarTodos();
    }

    @GetMapping("/teste")
    public String teste() {
        return "API no ar!";
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarIngrediente(@PathVariable Long id, @RequestBody Ingrediente ingrediente) {
        try {
            Ingrediente atualizado = ingredienteService.atualizar(id, ingrediente);
            return ResponseEntity.ok(atualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarIngrediente(@PathVariable Long id) {
        try {
            String msg = ingredienteService.deletar(id);
            return ResponseEntity.ok(msg);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
