package com.punkbolos.punkbolos_app.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        
     // Verifica duplicação de ingredientes
        Set<Long> idsIngredientes = new HashSet<>();
        for (ItemReceita item : receita.getItens()) {
            if (item.getIngrediente() == null || item.getIngrediente().getId() == null) {
                throw new IllegalArgumentException("Cada item da receita deve conter um ingrediente com ID válido.");
            }

            Long id = item.getIngrediente().getId();
            if (!idsIngredientes.add(id)) {
                throw new IllegalArgumentException("Ingrediente com ID " + id + " está duplicado na receita.");
            }
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
        
     // 💰 Calcula e define o custo total
        BigDecimal custoTotal = calcularCustoTotal(receita);
        receita.setCustoTotal(custoTotal);

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

    public Receita atualizar(Long id, Receita novaReceita) {
        Receita receitaExistente = receitaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Receita com ID " + id + " não encontrada."));

        if (novaReceita.getNome() == null || novaReceita.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome da receita é obrigatório.");
        }

        if (novaReceita.getItens() == null || novaReceita.getItens().isEmpty()) {
            throw new IllegalArgumentException("A receita deve conter ao menos um ingrediente.");
        }
        
     // Verifica duplicidade de ingredientes
        Set<Long> idsIngredientes = new HashSet<>();
        for (ItemReceita item : novaReceita.getItens()) {
            if (item.getIngrediente() == null || item.getIngrediente().getId() == null) {
                throw new IllegalArgumentException("Cada item da receita deve conter um ingrediente com ID válido.");
            }

            Long idIngrediente = item.getIngrediente().getId();
            if (!idsIngredientes.add(idIngrediente)) {
                throw new IllegalArgumentException("Ingrediente com ID " + idIngrediente + " está duplicado na receita.");
            }
        }

        // Atualiza o nome
        receitaExistente.setNome(novaReceita.getNome());

        // Limpa os itens antigos
        receitaExistente.getItens().clear();

        // Adiciona os novos itens com os ingredientes atualizados
        for (ItemReceita item : novaReceita.getItens()) {
            if (item.getIngrediente() == null || item.getIngrediente().getId() == null) {
                throw new IllegalArgumentException("Cada item da receita deve conter um ingrediente com ID válido.");
            }

            Ingrediente ingrediente = ingredienteRepository.findById(item.getIngrediente().getId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Ingrediente com ID " + item.getIngrediente().getId() + " não encontrado."));

            item.setIngrediente(ingrediente);
            item.setReceita(receitaExistente);

            receitaExistente.getItens().add(item);
        }

        // Recalcula o custo total
        receitaExistente.setCustoTotal(calcularCustoTotal(receitaExistente));

        return receitaRepository.save(receitaExistente);
    }

    
    private BigDecimal calcularCustoTotal(Receita receita) {
        BigDecimal total = BigDecimal.ZERO;

        for (ItemReceita item : receita.getItens()) {
            Ingrediente ingrediente = item.getIngrediente();

            if (ingrediente.getCustoEmbalagem() != null && ingrediente.getQuantidadeEmbalagem() != null
                    && ingrediente.getQuantidadeEmbalagem() > 0) {

                BigDecimal custoUnitario = ingrediente.getCustoEmbalagem()
                        .divide(BigDecimal.valueOf(ingrediente.getQuantidadeEmbalagem()), 4, RoundingMode.HALF_UP);

                BigDecimal custoItem = custoUnitario.multiply(BigDecimal.valueOf(item.getQuantidade()));
                total = total.add(custoItem);
            }
        }

        return total.setScale(2, RoundingMode.HALF_UP);
    }
}
