package com.example.taskmanagerkanban.repository;

import java.util.List;
import java.util.UUID;

public interface IRepository<E> {
    List<E> getList();
    E get(String id);
    void insertList(List<E>list);
    void insert(E e);

    void clear();
    void delete(E e);

    void update(E e);
    void addList(List<E>list);


}
