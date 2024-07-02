package edu.todo_list.controller;

import edu.todo_list.entity.Tarefas;
import edu.todo_list.service.TarefasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefasController {

    private final TarefasService service;

    public TarefasController(TarefasService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Tarefas>> findAll(){
        try{
            List<Tarefas> tarefasList = service.findAll();
            return ResponseEntity.ok().body(tarefasList);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarefas> findById(@PathVariable Long id){
        try {
            Tarefas tarefa = service.findById(id);
            return ResponseEntity.ok().body(tarefa);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Tarefas> save(@RequestBody Tarefas tarefa){
        try {
            Tarefas tarefas = service.save(tarefa);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(tarefa.getId())
                    .toUri();

            return ResponseEntity.created(location).body(tarefas);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Tarefas> uptaded(@PathVariable Long id, @RequestBody Tarefas tarefa){
        try{
            Tarefas tarefaAtualizar = service.updated(id, tarefa);
            return ResponseEntity.ok(tarefaAtualizar);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
