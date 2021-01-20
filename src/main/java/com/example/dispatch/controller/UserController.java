package com.example.dispatch.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import com.example.dispatch.model.Auth;
import com.example.dispatch.model.Responder;
import com.example.dispatch.model.UserView;
import com.example.dispatch.response.Response;
import com.example.dispatch.service.TokenAuthenticationService;
import com.example.dispatch.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {
    @Autowired
    private TokenAuthenticationService auth;
    
    @Autowired
    private UserService userService;
    
    @ApiOperation(value = "Login to system")
    @PostMapping("/login")
    public ResponseEntity<Response<UserView>> login(@Valid @RequestBody Auth auth) {
        UserView result = userService.loginUser(auth.getUsername(), auth.getPassword());
        return ResponseEntity.ok().body(new Response<UserView>("200", "SUCCESS", result));
    }

    @ApiOperation(value = "Get all agencies, dispatchers, responders")
    @GetMapping("/")
    public ResponseEntity<Response<Map<String, Object>>> getAllUsers(
    ) {
        Map<String, Object> result = userService.getAllUsers();
        return ResponseEntity.ok().body(new Response<>("200", "SUCCESS", result));
    }

    @ApiOperation(value = "Get all Responders belong to agency")
    @GetMapping("/responder")
    public ResponseEntity<Response<List<Responder>>> getResponders(
        @ApiParam(required = false, hidden = true) @RequestHeader(value = "Authorization", required = false) String authorizationToken
    ) {
        UUID agencyId = UUID.fromString(auth.getAgencyIdFromToken(authorizationToken));
        List<Responder> result = userService.getResponders(agencyId);
        return ResponseEntity.ok().body(new Response<>("200", "SUCCESS", result));
    }
}
