package com.jm.contract.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "contract_links")
public class ContractLinkData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonIgnore
    @JoinColumn (name = "client_id")
    @OneToOne
    private Client client;

    @Column(name = "contract_link")
    private String contractLink;

    @Column(name = "contract_name")
    private String contractName;

    public ContractLinkData() {
    }

    public Long getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getContractLink() {
        return contractLink;
    }

    public void setContractLink(String contractLink) {
        this.contractLink = contractLink;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }
}
