package com.jm.contract.repository.impl;

import com.jm.contract.repository.interfaces.ContractLinkDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
public class ContractLinkDataRepositoryImpl implements ContractLinkDataRepository {

    private final EntityManager entityManager;

    @Autowired
    public ContractLinkDataRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public void deleteContactLinkByClientId(Long id) {
        entityManager.createNativeQuery("DELETE FROM contract_links WHERE client_id = " + id)
                .executeUpdate();
    }
}