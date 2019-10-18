package com.jm.contract.services.impl;

import com.jm.contract.models.ContractSetting;
import com.jm.contract.repository.interfaces.ContractSettingRepository;
import com.jm.contract.services.interfaces.ContractSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContractSettingServiceImpl implements ContractSettingService {

    private final ContractSettingRepository contractSettingRepository;

    @Autowired
    public ContractSettingServiceImpl(ContractSettingRepository contractSettingRepository) {
        this.contractSettingRepository = contractSettingRepository;
    }

    @Override
    public boolean existsByHash(String hash) {
        return contractSettingRepository.existsByHash(hash);
    }

    @Override
    public Optional<ContractSetting> getByHash(String hash) {
        return Optional.of(contractSettingRepository.getByHash(hash));
    }

    @Override
    public void deleteByHash(String hash) {
        contractSettingRepository.deleteByHash(hash);
    }

    @Override
    public void save(ContractSetting setting) {
        contractSettingRepository.save(setting);
    }
}
