package com.jm.contract.services.impl;

import com.jm.contract.models.Client;
import com.jm.contract.models.ContractLinkData;
import com.jm.contract.repository.interfaces.ClientRepository;
import com.jm.contract.services.interfaces.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ClientServiceImpl extends CommonServiceImpl<Client> implements ClientService {

    private static Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);
    private final ClientRepository clientRepository;
//    private final PassportService passportService;
//    private final ProjectPropertiesService projectPropertiesService;
    private Environment env;
//    private final UserService userService;

	@Autowired
	public ClientServiceImpl(ClientRepository clientRepository
//							 PassportService passportService,
//							 ProjectPropertiesService projectPropertiesService,  UserService userService
	) {
		this.clientRepository = clientRepository;
//		this.passportService = passportService;
//		this.notificationRepository = notificationRepository;
//		this.projectPropertiesService = projectPropertiesService;
		this.env = env;
//		this.userService = userService;
	}

	@Override
	public Optional<Client> getClientByID(Long id) {
		return clientRepository.findById(id);
	}

//	@Transactional
//	@Override
//	public void updateClientFromContractForm(Client clientOld, ContractDataForm contractForm, User user) {
//		Client client = createUpdateClient(user, clientOld, contractForm);
//		Optional<ClientHistory> optionalHistory = clientHistoryService.createHistory(user, clientOld, client, ClientHistory.Type.UPDATE);
//		if (optionalHistory.isPresent()) {
//			ClientHistory history = optionalHistory.get();
//			if (history.getTitle() != null && !history.getTitle().isEmpty()) {
//				client.addHistory(history);
//			}
//		}
//		clientRepository.saveAndFlush(client);
//		logger.info("{} has updated client: id {}, email {}", user.getFullName(), client.getId(), client.getEmail().orElse("not found"));
//	}

	@Override
	public void setContractLink(Long clientId, String contractLink, String contractName) {
		Client client = clientRepository.getOne(clientId);
		ContractLinkData contractLinkData = new ContractLinkData();
		contractLinkData.setContractLink(contractLink);
		contractLinkData.setContractName(contractName);
		contractLinkData.setClient(client);
		client.setContractLinkData(contractLinkData);
		clientRepository.saveAndFlush(client);
	}

	}

