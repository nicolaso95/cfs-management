package com.example.dispatch.controller;

import java.util.Date;
import java.util.UUID;

import javax.validation.Valid;

import com.example.dispatch.model.CFSData;
import com.example.dispatch.model.CFSDataDTO;
import com.example.dispatch.model.PageData;
import com.example.dispatch.response.Response;
import com.example.dispatch.service.DispatchService;
import com.example.dispatch.service.TokenAuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/api/dispatch")
public class DispatchController {
    @Autowired
    private TokenAuthenticationService auth;
    
    @Autowired
    private DispatchService dispatchService;

    @ApiOperation(value = "Create a CFS")
    @PostMapping("")
    public ResponseEntity<Response<CFSData>> createCFS(
        @Valid @RequestBody CFSDataDTO data,
        @ApiParam(required = false, hidden = true) @RequestHeader(value = "Authorization", required = false) String authorizationToken
    ) {
        UUID agencyId = UUID.fromString(auth.getAgencyIdFromToken(authorizationToken));
        CFSData result = dispatchService.createCFS(agencyId, data);
        return ResponseEntity.ok().body(new Response<>("200", "SUCCESS", result));
    }

    @ApiOperation(value = "Update a CFS")
    @PutMapping("/{eventId}")
    public ResponseEntity<Response<CFSData>> updateCFS(
        @ApiParam(required = false, hidden = true) @RequestHeader(value = "Authorization", required = false) String authorizationToken,
        @PathVariable(value = "eventId") UUID eventId,
        @RequestBody CFSDataDTO newData
    ) {
        UUID agencyId = UUID.fromString(auth.getAgencyIdFromToken(authorizationToken));
        CFSData result = dispatchService.updateCFS(eventId, agencyId, newData);
        return ResponseEntity.ok().body(new Response<>("200", "SUCCESS", result));
    }

    @ApiOperation(value = "Search for CFS within a time range")
    @GetMapping("")
    public ResponseEntity<Response<PageData>> getCFSInTimeRange(
        @ApiParam(required = false, hidden = true) @RequestHeader(value = "Authorization", required = false) String authorizationToken,
        @RequestParam(value = "startTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS") Date startTime,
        @RequestParam(value = "endTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS") Date endTime,
        @RequestParam(value = "page") int page,
        @RequestParam(value = "limit") int limit
    ) {
        log.info("startTime: {}", startTime);
        log.info("endTime: {}", endTime);
        UUID agencyId = UUID.fromString(auth.getAgencyIdFromToken(authorizationToken));
        PageData result = dispatchService.getCFSInRangeTime(agencyId, startTime, endTime, page, limit);
        return ResponseEntity.ok().body(new Response<>("200", "SUCCESS", result));
    }

    @ApiOperation(value = "Search for CFS that assigned to a responder")
    @GetMapping("/{responder}")
    public ResponseEntity<Response<PageData>> getCFSByResponder(
        @ApiParam(required = false, hidden = true) @RequestHeader(value = "Authorization", required = false) String authorizationToken,
        @PathVariable(value = "responder") String responder,
        @RequestParam(value = "page") int page,
        @RequestParam(value = "limit") int limit
    ) {
        UUID agencyId = UUID.fromString(auth.getAgencyIdFromToken(authorizationToken));
        PageData result = dispatchService.getCFSByResponder(agencyId, responder, page, limit);
        return ResponseEntity.ok().body(new Response<>("200", "SUCCESS", result));
    }
    
    @ApiOperation(value = "Delete a CFS")
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Response<CFSData>> deleteCFS(
        @ApiParam(required = false, hidden = true) @RequestHeader(value = "Authorization", required = false) String authorizationToken,
        @PathVariable(value = "eventId") UUID eventId
    ) {
        UUID agencyId = UUID.fromString(auth.getAgencyIdFromToken(authorizationToken));
        dispatchService.deleteCFS(eventId, agencyId);
        return ResponseEntity.ok().body(new Response<>("200", "SUCCESS", null));
    }
}
