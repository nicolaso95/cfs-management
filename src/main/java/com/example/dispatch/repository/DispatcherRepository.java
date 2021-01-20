package com.example.dispatch.repository;

import java.util.List;
import java.util.UUID;

import com.example.dispatch.model.Dispatcher;

import org.springframework.data.repository.CrudRepository;

public interface DispatcherRepository extends CrudRepository<Dispatcher, UUID> {
    Dispatcher findByUsername(String username);
    List<Dispatcher> findAll();
}
