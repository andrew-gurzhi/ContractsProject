package com.jm.contract.Controllers.Rest;

import com.jm.contract.models.Client;
import com.jm.contract.models.ContractSetting;
import com.jm.contract.models.GoogleToken;
import com.jm.contract.services.interfaces.ClientService;
import com.jm.contract.services.interfaces.ContractSettingService;
import com.jm.contract.services.interfaces.GoogleTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/rest/client/contract")
public class ClientContractRestController {
    private static final Logger logger = LoggerFactory.getLogger(ClientContractRestController.class);

    private final ContractSettingService settingService;
    private final GoogleTokenService googleTokenService;
    private final ClientService clientService;

    public ClientContractRestController(ContractSettingService settingService, GoogleTokenService googleTokenService, ClientService clientService) {
        this.settingService = settingService;
        this.googleTokenService = googleTokenService;
        this.clientService = clientService;
    }

    @PostMapping("/create")
//    @PreAuthorize("hasAnyAuthority('OWNER', 'ADMIN', 'HR')")
    public ResponseEntity createContractSetting(@RequestBody ContractSetting setting
//                                                @AuthenticationPrincipal User userFromSession
    ) {
//        if (googleTokenService.getToken(GoogleToken.TokenType.DRIVE).isPresent()) {
//            Long clientId = setting.getClientId();
//            Client client = clientService.get(clientId);
//            if (client.getContractLinkData() == null) {
//                client.setOwnerUser(userFromSession);
//                clientService.update(client);
//                logger.info("User {} has assigned client with id {}", userFromSession.getEmail(), clientId);
//                setting.setUser(userFromSession);
                settingService.save(setting);
//                logger.info("{} create unique contract link for client id = {}", userFromSession.getFullName(), setting.getClientId());
                return new ResponseEntity(HttpStatus.OK);
//            } else {
//                logger.error("Client with id {} already have contract", clientId);
//                return new ResponseEntity(HttpStatus.ALREADY_REPORTED);
//            }
//        }
//        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}