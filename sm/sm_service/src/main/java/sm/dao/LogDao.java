package sm.dao;

import org.springframework.stereotype.Repository;
import sm.entity.Log;

import java.util.List;

@Repository
public interface LogDao {
    void insert(Log log);
    List<Log> selectByType(String type);
}
