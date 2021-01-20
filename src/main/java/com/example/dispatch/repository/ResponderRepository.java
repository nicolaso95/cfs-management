package com.example.dispatch.repository;

import java.util.List;
import java.util.UUID;

import com.example.dispatch.model.Responder;

import org.springframework.data.repository.CrudRepository;

public interface ResponderRepository extends CrudRepository<Responder, UUID> {
    List<Responder> findByAgency_Id(UUID agencyId);
    Responder findByName(String name);
    List<Responder> findAll();
}
