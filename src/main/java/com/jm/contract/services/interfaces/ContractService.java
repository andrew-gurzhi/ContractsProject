package com.jm.contract.services.interfaces;

import com.jm.contract.models.ContractDataForm;
import com.jm.contract.models.ContractLinkData;
import com.jm.contract.models.ContractSetting;

import java.io.File;
import java.util.Map;

public interface ContractService {

    Map<String,String> getContractIdByFormDataWithSetting(ContractDataForm data,String typeOfContract);

    boolean updateContractLink(ContractLinkData contractLinkData);

    void deleteContractFromGoogleDrive(String idFileInGoogleDrive);

//	File getContractFile(ContractDataForm data, ContractSetting setting);
}