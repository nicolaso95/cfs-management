package com.example.dispatch.repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.example.dispatch.model.CFSData;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DispatchRepository extends PagingAndSortingRepository<CFSData, UUID>{
    CFSData findFirstByEventIdAndAgencyId(UUID id, UUID agencyId);
    Page<CFSData> findAllByAgencyIdAndDispatchTimeBetween(UUID agencyId, Date startTime, Date endTime, Pageable pageable);
    Page<CFSData> findByAgencyIdAndResponder(UUID agencyId, String responder, Pageable pageable);
    List<CFSData> findByAgencyIdAndResponder(UUID agencyId, String responder);
}

