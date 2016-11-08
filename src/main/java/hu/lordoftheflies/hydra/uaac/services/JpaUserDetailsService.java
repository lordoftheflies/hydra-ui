/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.lordoftheflies.hydra.uaac.services;

import hu.lordoftheflies.hydra.uaac.dal.AccountRepository;
import hu.lordoftheflies.hydra.uaac.entities.AccountDetailsEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 *
 * @author lordoftheflies
 */
@Service
public class JpaUserDetailsService implements UserDetailsService {

    private static final Logger LOG = Logger.getLogger(JpaUserDetailsService.class.getName());

    protected static User toDto(UserDetails entity) {
        Collection<? extends GrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        User user = new User(
                entity.getUsername(),
                entity.getPassword(),
                entity.isEnabled(),
                entity.isAccountNonExpired(),
                entity.isCredentialsNonExpired(),
                entity.isAccountNonLocked(),
                authorities);
        return user;
    }

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountDetailsEntity accountEntity = accountRepository.findByUsername(username);
        if (accountEntity == null) {
            LOG.log(Level.INFO, "Sign-in failed for {0}", username);
            throw new UsernameNotFoundException("Account not found.");
        } else {
            LOG.log(Level.INFO, "Sign-in user {0}", username);
            return toDto(accountEntity);
        }
    }
}
