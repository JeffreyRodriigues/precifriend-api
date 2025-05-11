package com.pricefriend.api.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pricefriend.api.model.Ingrediente;
import com.pricefriend.api.repository.IngredienteRepository;

@Service
public class IngredienteService {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    public Ingrediente salvar(Ingrediente ingrediente) {
        validar(ingrediente);
        return ingredienteRepository.save(ingrediente);
    }

    public List<Ingrediente> listarTodos() {
        return ingredienteRepository.findAll();
    }

    public Ingrediente atualizar(Long id, Ingrediente novoIngrediente) {
        // Verifica se o ingrediente existe antes de atualizar
        if (!ingredienteRepository.existsById(id)) {
            throw new IllegalArgumentException("Ingrediente com ID " + id + " não encontrado.");
        }
        // Define o ID do ingrediente para garantir que estamos atualizando o existente
        novoIngrediente.setId(id);     
        // Reutiliza o método salvar para realizar a validação e a persistência
        return salvar(novoIngrediente);
    }

    public String deletar(Long id) {
        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ingrediente com ID " + id + " não encontrado."));

        ingredienteRepository.deleteById(id);
        return "Ingrediente '" + ingrediente.getNome() + "' foi deletado com sucesso.";
    }

    private void validar(Ingrediente ingrediente) {
        if (ingrediente.getNome() == null || ingrediente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do ingrediente não pode ser vazio.");
        }

        // Verifica se já existe um ingrediente com o mesmo nome, ignorando maiúsculas/minúsculas
        Optional<Ingrediente> ingredienteExistente = ingredienteRepository.findByNomeIgnoreCase(ingrediente.getNome());
        if (ingredienteExistente.isPresent()) {
            throw new IllegalArgumentException("Já existe um ingrediente com o nome '" + ingrediente.getNome() + "'.");
        }

        if (ingrediente.getQuantidadeEmbalagem() == null || ingrediente.getQuantidadeEmbalagem() <= 0) {
            throw new IllegalArgumentException("A quantidade da embalagem deve ser maior que 0g.");
        }

        if (ingrediente.getCustoEmbalagem() == null || ingrediente.getCustoEmbalagem().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O custo da embalagem deve ser maior que 0.");
        }
    }

}
