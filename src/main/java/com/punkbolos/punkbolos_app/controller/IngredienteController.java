package com.punkbolos.punkbolos_app.controller;

import java.util.List;
import java.util.Optional;

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

import com.punkbolos.punkbolos_app.model.Ingrediente;
import com.punkbolos.punkbolos_app.repository.IngredienteRepository;

@RestController
@RequestMapping("/api/ingredientes")
public class IngredienteController {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @PostMapping
    public Ingrediente adicionarIngrediente(@RequestBody Ingrediente ingrediente) {
        return ingredienteRepository.save(ingrediente);
    }
    
    @GetMapping("/teste")
    public String teste() {
        return "API no ar!";
    }

    @GetMapping
    public List<Ingrediente> listarIngredientes() {
        return ingredienteRepository.findAll();
    }

    @PutMapping("/{id}")
    public Ingrediente atualizarIngrediente(@PathVariable Long id, @RequestBody Ingrediente ingrediente) {
        Ingrediente existente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingrediente não encontrado"));
        existente.setNome(ingrediente.getNome());
        existente.setQuantidadeEmbalagem(ingrediente.getQuantidadeEmbalagem());
        existente.setCustoEmbalagem(ingrediente.getCustoEmbalagem());
        return ingredienteRepository.save(existente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarIngrediente(@PathVariable Long id) {
        Optional<Ingrediente> ingredienteOptional = ingredienteRepository.findById(id);

        if (ingredienteOptional.isPresent()) {
            Ingrediente ingrediente = ingredienteOptional.get();
            String nome = ingrediente.getNome();
            ingredienteRepository.deleteById(id);
            return ResponseEntity.ok("Ingrediente '" + nome + "' foi deletado com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ingrediente com ID " + id + " não encontrado.");
        }
    }
}
