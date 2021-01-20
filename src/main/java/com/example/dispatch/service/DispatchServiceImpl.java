package com.example.dispatch.service;

import java.util.Date;
import java.util.UUID;

import com.example.dispatch.exception.ConflictException;
import com.example.dispatch.exception.NotFoundException;
import com.example.dispatch.model.CFSData;
import com.example.dispatch.model.CFSDataDTO;
import com.example.dispatch.model.Responder;
import com.example.dispatch.model.PageData;
import com.example.dispatch.repository.DispatchRepository;
import com.example.dispatch.repository.ResponderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DispatchServiceImpl implements DispatchService {
    
    @Autowired
    private DispatchRepository dispatchRepo;
    @Autowired
    private ResponderRepository responderRepo;
    
    @Override
    public CFSData createCFS(UUID agencyId, CFSDataDTO data) {
        Responder responder = responderRepo.findByName(data.getResponder());
        if(responder == null) {
            throw new NotFoundException("Responder is not found");
        }
        log.info("responder: {}", responder);

        if(!responder.getAgency().getId().equals(agencyId)) {
            throw new ConflictException("Responder is not belong to dispatcher's agency");
        }

        CFSData cfs = new CFSData();
        cfs.setAgency(responder.getAgency());
        cfs.setEventNumber(data.getEventNumber());
        cfs.setEventTypeCode(data.getEventTypeCode());
        cfs.setResponder(data.getResponder());
        return dispatchRepo.save(cfs);
    }

    @Override
    public CFSData updateCFS(UUID eventId, UUID agencyId, CFSDataDTO newData) {
        CFSData updateCfs = dispatchRepo.findFirstByEventIdAndAgencyId(eventId, agencyId);
        if (updateCfs == null) {
            throw new NotFoundException("Event is not existed");
        }
        if (newData.getEventNumber() != null) {
            updateCfs.setEventNumber(newData.getEventNumber());
        }
        if (newData.getEventTypeCode() != null) {
            updateCfs.setEventTypeCode(newData.getEventTypeCode());
        }
        if (newData.getResponder() != null) {
            updateCfs.setResponder(newData.getResponder());
        }
        return dispatchRepo.save(updateCfs);
    }

    @Override
    public PageData getCFSInRangeTime(UUID agencyId, Date startTime, Date endTime, int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by("dispatchTime").descending());
        Page<CFSData> pageData = dispatchRepo.findAllByAgencyIdAndDispatchTimeBetween(agencyId, startTime, endTime, pageable);

        PageData result = new PageData();
        result.setLimit(limit);
        result.setPage(page);
        result.setTotalPages(pageData.getTotalPages());
        result.setData(pageData.getContent());
        return result;
    }

    @Override
    public PageData getCFSByResponder(UUID agencyId, String responder, int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by("dispatchTime").descending());
        Page<CFSData> pageData = dispatchRepo.findByAgencyIdAndResponder(agencyId, responder, pageable);

        PageData result = new PageData();
        result.setLimit(limit);
        result.setPage(page);
        result.setTotalPages(pageData.getTotalPages());
        result.setData(pageData.getContent());
        return result;
    }

    @Override
    public void deleteCFS(UUID eventId, UUID agencyId) {
        CFSData deleteCfs = dispatchRepo.findFirstByEventIdAndAgencyId(eventId, agencyId);
        if (deleteCfs == null) {
            throw new NotFoundException("Event is not existed");
        }
        dispatchRepo.delete(deleteCfs);
    }
}
