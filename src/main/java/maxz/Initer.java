package maxz;

import maxz.entity.Task;
import maxz.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Service
public class Initer {

    @Autowired
    private TaskRepository repository;

    @PostConstruct
    public void init() {
        List<Task> data = Stream.generate(() -> {
            Task entity = new Task();
            entity.data = UUID.randomUUID().toString().substring(0, 3);
            return entity;
        }).limit(5).collect(toList());
        data.forEach(x->repository.save(x));
    }

}
