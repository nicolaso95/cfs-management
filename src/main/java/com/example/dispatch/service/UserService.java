package com.example.dispatch.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.example.dispatch.model.Responder;
import com.example.dispatch.model.UserView;

public interface UserService {
   public abstract UserView loginUser(String username, String password);
   public abstract Map<String, Object> getAllUsers();
   public abstract List<Responder> getResponders(UUID agencyId);
}

