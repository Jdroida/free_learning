package sm.dao;

import org.springframework.stereotype.Repository;
import sm.entity.Staff;

@Repository
public interface SelfDao {
    Staff selectByAccount(String account);
}

