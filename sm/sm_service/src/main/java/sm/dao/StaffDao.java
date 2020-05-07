package sm.dao;

import org.springframework.stereotype.Repository;
import sm.entity.Staff;

import java.util.List;

@Repository
public interface StaffDao {
    void insert(Staff staff);
    void delete(Integer id);
    void update(Staff staff);
    Staff selectById(Integer id);
    List<Staff> selectAll();
}
