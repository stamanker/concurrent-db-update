package maxz.controllers;

import lombok.extern.slf4j.Slf4j;
import maxz.Processor;
import maxz.entity.*;
import maxz.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController("/")
@RequestMapping(value = "/")
@Slf4j
public class Controller {

    @Autowired
    Processor processor;
    @Autowired
    TaskRepository repository;

    @GetMapping("/")
    public Iterable<Task> getAllData() {
        return getData(null);
    }

    @GetMapping("/{state}")
    public Iterable<Task> getData(@PathVariable(value = "state", required = false) Optional<Integer> state) {
        System.out.println("Controller.getData by state: " + state);
        //log.info("get by state = {}", state);
        if(state.isPresent()) {
            return repository.findAll();
        }
        return repository.findByState(state.get());
    }
}
