package uz.pdp.chat.db;

import java.util.List;

public interface Repository<T> {
    void save(T t);

    List<T> findAll();

    void delete(T t);
}

