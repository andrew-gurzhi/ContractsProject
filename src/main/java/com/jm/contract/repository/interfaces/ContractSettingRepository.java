package com.jm.contract.repository.interfaces;


import com.jm.contract.models.ContractSetting;

public interface ContractSettingRepository extends CommonGenericRepository<ContractSetting> {

    boolean existsByHash(String hash);

    void deleteByHash(String hash);

    ContractSetting getByHash(String hash);
}
