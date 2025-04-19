package com.punkbolos.punkbolos_app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.punkbolos.punkbolos_app.model.Ingrediente;
import com.punkbolos.punkbolos_app.model.ItemReceita;
import com.punkbolos.punkbolos_app.model.Receita;
import com.punkbolos.punkbolos_app.repository.IngredienteRepository;
import com.punkbolos.punkbolos_app.repository.ReceitaRepository;

@Service
public class ReceitaService {

    @Autowired
    private ReceitaRepository receitaRepository;
    
    @Autowired
    private IngredienteRepository ingredienteRepository;

    public Receita salvar(Receita receita) {
        if (receita.getNome() == null || receita.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome da receita é obrigatório.");
        }

        if (receita.getItens() == null || receita.getItens().isEmpty()) {
            throw new IllegalArgumentException("A receita deve conter ao menos um ingrediente.");
        }

        // Associa cada item com a receita e busca o ingrediente completo
        for (ItemReceita item : receita.getItens()) {
            // Verifica se o ID do ingrediente foi passado
            if (item.getIngrediente() == null || item.getIngrediente().getId() == null) {
                throw new IllegalArgumentException("Cada item da receita deve conter um ingrediente com ID válido.");
            }

            // Busca o ingrediente completo no banco
            Ingrediente ingrediente = ingredienteRepository.findById(item.getIngrediente().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Ingrediente com ID " + item.getIngrediente().getId() + " não encontrado."));

            item.setIngrediente(ingrediente); // Garante que é uma entidade gerenciada
            item.setReceita(receita);         // Associa o item à receita
        }

        return receitaRepository.save(receita);
    }

    
    public List<Receita> listarTodas() {
        return receitaRepository.findAll();
    }

    public Optional<Receita> buscarPorId(Long id) {
        return receitaRepository.findById(id);
    }
    
    public String deletar(Long id) {
        Receita receita = receitaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Receita com ID " + id + " não encontrada."));

        // Obtém o nome da receita
        String nomeReceita = receita.getNome();

        // Deleta a receita
        receitaRepository.delete(receita);

        // Retorna a mensagem com o nome da receita
        return "Receita '" + nomeReceita + "' deletada com sucesso.";
    }

    public Receita atualizar(Long id, Receita receitaAtualizada) {
        // Verifica se a receita existe no banco
        Receita receitaExistente = receitaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Receita com ID " + id + " não encontrada."));

        // Atualiza o nome da receita se o nome foi alterado
        if (receitaAtualizada.getNome() != null && !receitaAtualizada.getNome().isBlank()) {
            receitaExistente.setNome(receitaAtualizada.getNome());
        }

        // Atualiza os itens da receita
        if (receitaAtualizada.getItens() != null && !receitaAtualizada.getItens().isEmpty()) {
            receitaExistente.setItens(receitaAtualizada.getItens());
        }

        // Salva as mudanças
        return receitaRepository.save(receitaExistente);
    }

}
