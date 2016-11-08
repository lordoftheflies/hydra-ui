/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.lordoftheflies.hydra.uaac.dal;

import hu.lordoftheflies.hydra.uaac.entities.AccountDetailsEntity;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lordoftheflies
 */
@Repository
public interface AccountRepository extends CrudRepository<AccountDetailsEntity, UUID> {

    AccountDetailsEntity findByUsername(@Param("username") String username);

}
