package com.jm.contract.services.interfaces;

import com.jm.contract.models.Client;
import com.jm.contract.models.ContractDataForm;
import com.jm.contract.models.User;

import java.util.Optional;

public interface ClientService extends CommonService<Client> {

	Optional<Client> getClientByID(Long id);


//	void updateClient(Client client);
//
//	void updateClientFromContractForm(Client client, ContractDataForm contractForm, User authUser);

	void setContractLink(Long clientId, String contractLink, String contractName);


}