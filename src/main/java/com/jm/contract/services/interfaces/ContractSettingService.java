package com.jm.contract.services.interfaces;


import com.jm.contract.models.ContractSetting;

import java.util.Optional;

public interface ContractSettingService {

    boolean existsByHash(String hash);

    Optional<ContractSetting> getByHash(String hash);

    void deleteByHash(String hash);

    void save(ContractSetting setting);
}
