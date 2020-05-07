package sm.dao;

import org.springframework.stereotype.Repository;
import sm.entity.Department;

import java.util.List;
@Repository
public interface DepartmentDao {
    void insert(Department department);

    void delete(Integer id);

    void update(Department department);

    Department selectById(Integer id);

    List<Department> selectAll();
}
