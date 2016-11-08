/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.lordoftheflies.hydra.uaac.entities;

import hu.lordoftheflies.hydra.entities.UniqueEntity;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author lordoftheflies
 */
@Entity
@Table(name = "user_roles")
public class AuthorityEntity extends UniqueEntity implements GrantedAuthority {

    @ManyToMany(mappedBy = "authorities")
    private List<AccountDetailsEntity> owners;

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    

    @Override
    public String toString() {
        return "hu.lordoftheflies.hydra.entities.AuthorityEntity[ id=" + id + " ]";
    }

}
