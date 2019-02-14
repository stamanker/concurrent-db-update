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

import java.util.List;

@RestController("/")
@RequestMapping(value = "/")
@Slf4j
public class Controller {

    @Autowired
    Processor processor;
    @Autowired
    TaskRepository repository;

    @GetMapping("/")
    public List<Task> getAllData() {
        return getData(null);
    }

    @GetMapping("/{state}")
    public List<Task> getData(@PathVariable(value = "state", required = false) Integer state) {
        System.out.println("Controller.getData");
        //log.info("get by state = {}", state);
        return repository.findByState(state);
    }
}
