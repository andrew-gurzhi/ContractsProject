package com.jm.contract.repository.interfaces;

import com.jm.contract.models.ContractLinkData;

import java.util.Optional;

public interface ClientsContractLinkRepository extends CommonGenericRepository<ContractLinkData> {
    Optional<ContractLinkData> getByClientId(Long id);
}