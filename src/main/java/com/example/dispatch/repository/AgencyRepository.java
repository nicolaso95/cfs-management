package com.example.dispatch.repository;

import java.util.List;
import java.util.UUID;

import com.example.dispatch.model.Agency;

import org.springframework.data.repository.CrudRepository;

public interface AgencyRepository extends CrudRepository<Agency, UUID> {
    List<Agency> findAll();
}
