package lk.ijse.CarHire.dao;

import java.util.List;

public interface CrudDao<T> extends SuperDao {
    public List<T> getAll();
    public boolean save(T entity);
    public boolean update(T entity);
    public String generateNewId();
    public boolean delete(T entity);
    public T search(Integer id);
    public T findById(Integer customerId);
}