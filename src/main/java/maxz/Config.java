package maxz;

import maxz.entity.Task;
import maxz.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Configuration
@EnableScheduling
public class Config {

    @Autowired
    TaskRepository repository;

    @PostConstruct
    public void init() {
        List<Task> data = Stream.generate(() -> {
            Task entity = new Task();
            entity.data = UUID.randomUUID().toString();
            return entity;
        }).limit(100).collect(toList());
        data.forEach(x->repository.save(x));
    }

    @Bean(name = "executor-1", destroyMethod = "shutdown")
    public Executor taskScheduler() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5, new ThreadFactory() {
            AtomicInteger counter = new AtomicInteger();
            @Override
            public Thread newThread(Runnable r) {
                Thread result = new Thread(r);
                result.setName("thread-x-"+counter.incrementAndGet());
                result.setDaemon(true);
                return result;
            }
        });
        return executor;
    }

    @Bean(name = "executor-2", destroyMethod="shutdown")
    public ThreadPoolTaskExecutor getTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(1);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setThreadNamePrefix("thread-");
        executor.initialize();
        return executor;
    }

//    @Override
//    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
//        return new AsyncExceptionHandler();
//    }
}
