/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.lordoftheflies.hydra.uaac.dal;

import hu.lordoftheflies.hydra.uaac.entities.AccountDetailsEntity;
import hu.lordoftheflies.hydra.uaac.entities.AuthorityEntity;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lordoftheflies
 */
@Repository
public interface AuthorityRepository extends CrudRepository<AuthorityEntity, UUID>{
    
}
