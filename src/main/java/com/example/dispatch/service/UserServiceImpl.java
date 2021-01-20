package com.example.dispatch.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.example.dispatch.model.Agency;
import com.example.dispatch.model.Dispatcher;
import com.example.dispatch.model.Responder;
import com.example.dispatch.repository.AgencyRepository;
import com.example.dispatch.repository.DispatcherRepository;
import com.example.dispatch.repository.ResponderRepository;
import com.example.dispatch.model.UserPrincipal;
import com.example.dispatch.model.UserView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenAuthenticationService authService;

    @Autowired
    DispatcherRepository userRepo;

    @Autowired
    private ResponderRepository responderRepo;
    
    @Autowired
    private AgencyRepository agencyRepo;

    @Autowired
    private DispatcherRepository dispatcherRepo;

    @Override
    public UserView loginUser(String username, String password) {
        String jwt = generateUserToken(username, password);
        Dispatcher user = userRepo.findByUsername(username);
        return new UserView(user.getId(), user.getName(), user.getUsername(), jwt);
    }

    @Override
    public Map<String, Object> getAllUsers() {
        Map<String, Object> result = new HashMap<>();
        List<Responder> responders = responderRepo.findAll();
        List<Agency> agencies = agencyRepo.findAll();
        log.info("agencies: {}", agencies);
        List<Dispatcher> dispatchers = dispatcherRepo.findAll();
        result.put("responders", responders);
        result.put("agencies", agencies);
        result.put("dispatchers", dispatchers);
        return result;
    }

    @Override
    public List<Responder> getResponders(UUID agencyId) {
        return responderRepo.findByAgency_Id(agencyId);
    }

    public Dispatcher loadUser(String username) {
        return userRepo.findByUsername(username);
    }

    private String generateUserToken(String email, String password) {
        System.out.println("Before Authentication: " + email + " " + password);
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(email, password));
        System.out.println("After Authentication: " + authentication.getPrincipal().toString());
        return authService.generateToken(authentication);
    }

    public Dispatcher authenticate(String email, String password, Map<String, String> results) {
        String jwt = generateUserToken(email, password);
        results.put("token", jwt);
        return userRepo.findByUsername(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername: " + username);
        Dispatcher user = loadUser(username);
        if (user == null || user.isEmpty()) {
            throw new UsernameNotFoundException("Cannot find username.");
        }
        return UserPrincipal.create(user);
    }
}
