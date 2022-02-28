package com.sample.split.resource;

import com.sample.split.domain.accounting.Accounting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountingResource {
    
    @Autowired
    private Accounting accounting;

    public void accountingByProduct() {
        accounting.accountingByProduct();
    }

    public void accountingByChannel() {
        accounting.accountingByChannel();
    }
}
