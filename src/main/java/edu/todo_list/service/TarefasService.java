package edu.todo_list.service;

import edu.todo_list.entity.Tarefas;
import edu.todo_list.repository.TarefasRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarefasService {

    private final TarefasRepository tarefasRepository;


    public TarefasService(TarefasRepository tarefasRepository) {
        this.tarefasRepository = tarefasRepository;
    }

    public List<Tarefas> findAll(){

        if (tarefasRepository == null){
            throw new RuntimeException("O repositório de tarefas não está inicializado.");
        }
        try {
            return tarefasRepository.findAll();
        }catch (RuntimeException e){
            throw new RuntimeException("Não foi possível encontrar tarefas.", e);
        }
    }

    public Tarefas findById(Long id){

        try {
          return tarefasRepository.findById(id).orElseThrow(
                  () -> new RuntimeException("A tarefa com o ID fornecido não existe."));
        }catch (RuntimeException e){
            throw new RuntimeException("Não foi possível encontrar a tarefa com o ID fornecido.", e);
        }
    }

    public Tarefas save(Tarefas tarefa){
        try {
            return tarefasRepository.save(tarefa);
        }catch (RuntimeException e){
            throw new RuntimeException("Não foi possivel criar essa tarefa.", e);
        }
    }

    public Tarefas updated(Long id, Tarefas tarefa){
        try {

            Tarefas tarefaExistente = tarefasRepository.findById(id)
                    .orElseThrow(()-> new RuntimeException("A tarefa informada não existe."));
            
            tarefaExistente.setNome(tarefa.getNome());
            tarefaExistente.setDescricao(tarefa.getDescricao());
            tarefaExistente.setPrioridade(tarefa.getPrioridade());

            return tarefasRepository.save(tarefaExistente);
        }catch (RuntimeException e){
            throw new RuntimeException("Não foi possivel atualizar a tarefa informada.");
        }
    }

    public void delete(Long id){
        try{
            Tarefas tarefaDeletar = tarefasRepository.findById(id)
                    .orElseThrow(()-> new RuntimeException("A tarefa informada não existe."));

            tarefasRepository.delete(tarefaDeletar);
        }catch (RuntimeException e){
            throw new RuntimeException("Não foi possivel deletar a tarefa informada.", e);
        }
    }

}
