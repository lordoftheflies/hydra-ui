/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.lordoftheflies.hydra.ui.services;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lordoftheflies
 */
@RestController
@RequestMapping(value = "/view")
public class ApplicationController {

    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public Principal user(Principal user) {
        return user;
    }
    
    @RequestMapping(path = "/login", method = RequestMethod.POST, 
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public Principal login(Principal user) {
        return user;
    }

    @RequestMapping("/main-menu")
    public Map<String, Object> home() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", "Hello World");
        return model;
    }
}
