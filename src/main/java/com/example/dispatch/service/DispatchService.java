package com.example.dispatch.service;

import java.util.Date;
import java.util.UUID;

import com.example.dispatch.model.CFSData;
import com.example.dispatch.model.CFSDataDTO;
import com.example.dispatch.model.PageData;

public interface DispatchService {
    public abstract CFSData createCFS(UUID agencyId, CFSDataDTO data);
    public abstract CFSData updateCFS(UUID eventId, UUID agencyId, CFSDataDTO newData);
    public abstract PageData getCFSInRangeTime(UUID agencyId, Date startTime, Date endTime, int page, int limit);
    public abstract PageData getCFSByResponder(UUID agencyId, String responder, int page, int limit);
    public abstract void deleteCFS(UUID eventId, UUID agencyId);
}
