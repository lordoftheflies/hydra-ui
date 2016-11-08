package hu.lordoftheflies.hydra.ui;

import hu.lordoftheflies.hydra.uaac.dal.AccountRepository;
import hu.lordoftheflies.hydra.uaac.dal.AuthorityRepository;
import hu.lordoftheflies.hydra.uaac.entities.AccountDetailsEntity;
import hu.lordoftheflies.hydra.uaac.entities.AuthorityEntity;
import java.util.Arrays;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UaacMicroServiceTest {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AuthorityRepository authorityRepository;

    @Test
    public void aDeleteData() {
        accountRepository.deleteAll();
        authorityRepository.deleteAll();
    }

    @Test
    public void bInitialData() {
        AuthorityEntity viewerAuthority = new AuthorityEntity();
        viewerAuthority.setAuthority("VIEWER");
        authorityRepository.save(viewerAuthority);

        AuthorityEntity analystAuthority = new AuthorityEntity();
        analystAuthority.setAuthority("ANALYST");
        authorityRepository.save(analystAuthority);

        AuthorityEntity technicianAuthority = new AuthorityEntity();
        technicianAuthority.setAuthority("TECHNICIAN");
        authorityRepository.save(technicianAuthority);

        AuthorityEntity administratorAuthority = new AuthorityEntity();
        administratorAuthority.setAuthority("ADMINISTRATOR");
        authorityRepository.save(administratorAuthority);

        AccountDetailsEntity lordofthefliesAccount = new AccountDetailsEntity("lordoftheflies", "qwe123", true, true, true, true,
                Arrays.asList(viewerAuthority, analystAuthority, technicianAuthority, administratorAuthority));
        accountRepository.save(lordofthefliesAccount);
    }

}
