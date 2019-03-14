package maxz;

import maxz.entity.Task;
import maxz.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class Processor {

    @Autowired
    TaskRepository repo;

    @Scheduled(fixedDelay = 10)
    @Transactional()
    public void process1() throws Exception {
        try {
            Task task = getTasks();
            System.out.println("process1: " + task + " by " + Thread.currentThread().getName());
            task.data = task.data + " by 1";
            task.state = 1;
            Thread.sleep(2_000);
            repo.save(task);
            System.out.println("process1 done: " + task.id);
            System.out.println("process1 read same = " + repo.findById(task.id));
        } catch (EmptyResultDataAccessException e) {
            Thread.sleep(10_000);
        }
    }

    @Scheduled(fixedDelay = 15)
    @Transactional()
    public void process2() throws Exception {
        try {
            Task task = getTasks();
            System.out.println("process2: " + task + " by " + Thread.currentThread().getName());
            task.data = task.data + " by 2";
            task.state = 1;
            Thread.sleep(500);
            repo.save(task);
            System.out.println("process2 done: " + task.id);
            System.out.println("process2 read same = " + repo.findById(task.id));
        } catch (EmptyResultDataAccessException e) {
            Thread.sleep(10_000);
        }
    }

    private Task getTasks() {
        Page<Task> unprocessed = repo.findUnprocessed(PageRequest.of(0, 1));
        if(unprocessed.getTotalElements()==0) {
            throw new EmptyResultDataAccessException("no", 1);
        }
        return unprocessed.getContent().get(0);
    }


}
