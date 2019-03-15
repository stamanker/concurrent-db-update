package maxz.repositories;


import maxz.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;

@Repository
public interface TaskRepository extends PagingAndSortingRepository<Task, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select t from Task t where t.state = 0")
    Page<Task> findUnprocessed(Pageable p);

    List<Task> findByState(Integer state);
}
