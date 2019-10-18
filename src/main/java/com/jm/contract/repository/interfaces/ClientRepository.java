package com.jm.contract.repository.interfaces;

import com.jm.contract.models.Client;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ClientRepository extends CommonGenericRepository<Client> {


	Client getClientById(Long id);

	Client getClientByClientPhonesEquals(String phoneNumber);


}
