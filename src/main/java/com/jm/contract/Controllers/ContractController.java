package com.jm.contract.Controllers;

import com.jm.contract.Configs.GoogleAPIConfigImpl;
import com.jm.contract.models.ContractDataForm;
import com.jm.contract.models.ContractLinkData;
import com.jm.contract.repository.interfaces.ClientsContractLinkRepository;
import com.jm.contract.repository.interfaces.ContractLinkDataRepository;
import com.jm.contract.services.interfaces.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/contract")
public class ContractController {

	private static final Logger logger = LoggerFactory.getLogger(ContractController.class);

	private final ContractService contractService;
	private final ClientService clientService;
	private final ContractSettingService contractSettingService;
	private final ProjectPropertiesService projectPropertiesService;
	private final MailSendService mailSendService;
	private final GoogleAPIConfigImpl googleAPIConfig;
	private final ContractLinkDataRepository contractLinkRepository;
	private final ClientsContractLinkRepository clientsContractLinkRepository;
	private Environment environment;

	@Autowired
	public ContractController(ContractService contractService,
							  ClientService clientService,
							  ContractSettingService contractSettingService,
							  ProjectPropertiesService projectPropertiesService,
							  MailSendService mailSendService,
							  GoogleAPIConfigImpl googleAPIConfig,
							  ContractLinkDataRepository contractLinkRepository,
							  ClientsContractLinkRepository clientsContractLinkRepository,
							  Environment environment) {
		this.contractService = contractService;
		this.clientService = clientService;
		this.contractSettingService = contractSettingService;
		this.projectPropertiesService = projectPropertiesService;
		this.mailSendService = mailSendService;
		this.googleAPIConfig = googleAPIConfig;
		this.contractLinkRepository = contractLinkRepository;
		this.clientsContractLinkRepository = clientsContractLinkRepository;
		this.environment = environment;
	}

	@GetMapping("/java/")
	public ModelAndView completeFormJava() {
		ModelAndView model = new ModelAndView("contract");
		String typeOfContract = "java";
		model.addObject("data", new ContractDataForm());
		model.addObject("typeofcontract", typeOfContract);
		return model;
	}

	@GetMapping("/web/")
	public ModelAndView completeFormWeb( ) {
		ModelAndView model = new ModelAndView("contract");
		String typeOfContract = "web";
		model.addObject("data", new ContractDataForm());
		model.addObject("typeofcontract", typeOfContract);
		return model;
	}

	@Transactional
	@PostMapping("/{typeofcontract}")
	public String response(@ModelAttribute ContractDataForm data, @PathVariable("typeofcontract") String typeOfContract) {

		Map<String, String> contractDataMap = contractService.getContractIdByFormDataWithSetting(data,typeOfContract);
		String docLink = "";
		if (!contractDataMap.isEmpty()) {

			String googleDocUrl = googleAPIConfig.getDocsUri();
			String googleContractId = contractDataMap.get("contractId");
			docLink = googleDocUrl + googleContractId + "/edit?usp=sharing";
			//Сервис отправки сообщений пока не нужен, заккоментировали
//					clientService.setContractLink(clientId, docLink, contractDataMap.get("contractName"));
//			ProjectProperties current = projectPropertiesService.get();
//			if (current.getContractTemplate() != null) {
//				String contractTheme = environment.getRequiredProperty("contract.email.theme");
//				mailSendService.prepareAndSend(data, current.getContractTemplate().getTemplateText(), null, contractTheme, docLink);
//			}
			return "redirect:/success" ;
		}else
		return "redirect:/error" ;
	}

//	@GetMapping("/updateLink")
//	public ResponseEntity<String> updateContractLink(@RequestParam Long id) {
//		Client client = clientService.get(id);
//		//если обновилась отправить письмо
//		if (contractService.updateContractLink(client.getContractLinkData())) {
//			String contractTheme = environment.getRequiredProperty("contract.email.theme");
//			mailSendService.prepareAndSend(id, projectPropertiesService.getOrCreate().getContractTemplate().getTemplateText(), StringUtils.EMPTY, null, contractTheme);
//		}
//		return new ResponseEntity<>(client.getContractLinkData().getContractLink(), HttpStatus.OK);
//	}

	@DeleteMapping("/deleteContract")
	public ResponseEntity deleteContract(@RequestParam Long id) {
		Optional<ContractLinkData> contractLinkData = clientsContractLinkRepository.getByClientId(id);
		if (contractLinkData.isPresent()) {
			contractService.deleteContractFromGoogleDrive(contractLinkData.get().getContractLink());
			contractLinkRepository.deleteContactLinkByClientId(id);
		}
		return ResponseEntity.ok(HttpStatus.OK);
	}
}
