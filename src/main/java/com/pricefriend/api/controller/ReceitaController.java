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

import com.pricefriend.api.dto.ReceitaCustoDTO;
import com.pricefriend.api.model.Receita;
import com.pricefriend.api.service.ReceitaService;

@RestController
@RequestMapping("/api/receitas")
public class ReceitaController {

    @Autowired
    private ReceitaService receitaService;

    @PostMapping
    public ResponseEntity<?> criarReceita(@RequestBody Receita receita) {
        try {
            Receita salva = receitaService.salvar(receita);
            return ResponseEntity.ok(salva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<Receita> listarReceitas() {
        return receitaService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return receitaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarReceita(@PathVariable Long id) {
        try {
            // Chama o service e recebe a mensagem
            String mensagem = receitaService.deletar(id);
            return ResponseEntity.ok(mensagem);  // Exibe a mensagem com o nome da receita
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarReceita(@PathVariable Long id, @RequestBody Receita receita) {
        try {
            // Chama o service para atualizar a receita
            Receita receitaAtualizada = receitaService.atualizar(id, receita);
            return ResponseEntity.ok(receitaAtualizada);  // Retorna a receita atualizada
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // Caso não encontre a receita
        }
    }
    
    @GetMapping("/{id}/calcular")
    public ResponseEntity<?> calcularPrecoReceita(@PathVariable Long id) {
        try {
            ReceitaCustoDTO custoDTO = receitaService.calcularCusto(id);
            return ResponseEntity.ok(custoDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
