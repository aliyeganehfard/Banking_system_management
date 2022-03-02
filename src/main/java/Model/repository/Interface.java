package Model.repository;

import java.util.List;

public interface Interface<T> {
    void save(T t);
    void update(T t);
    void delete(Integer id);
    T findById(Integer id);
    List<T> findAll();
}
