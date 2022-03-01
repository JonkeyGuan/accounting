package com.sample.accounting.domain;

import java.util.List;

import com.sample.accounting.domain.accounting.AccountingItem;
import com.sample.accounting.domain.accounting.AccountingItemAccounting;

import org.springframework.stereotype.Component;

@Component("businessUnit")
public class BusinessUnit extends AccountingItemAccounting {

    @Override
    public List<AccountingItem> accountForProduct(List<AccountingItem> items) {
        // "channel", "market", "buyer", "businessGroup", "businessUnit", "product"
        List<String> includeCondition = List.of("ignore", "ignore", "ignore", "businessGroup", "ignore", "ignore");
        List<String> excludeCondition = List.of("channel", "market", "ignore", "businessGroup", "businessUnit", "product");
        return account(items, "businessUnit", includeCondition, excludeCondition);
    }

    @Override
    public List<AccountingItem> accountForChannel(List<AccountingItem> items) {
        // "channel", "market", "buyer", "businessGroup", "businessUnit", "product"
        List<String> includeCondition = List.of("channel", "market", "buyer", "businessGroup", "ignore", "ignore");
        List<String> excludeCondition = List.of("channel", "market", "ignore", "businessGroup", "businessUnit", "ignore");
        return account(items, "businessUnit", includeCondition, excludeCondition);
    }

    @Override
    protected boolean cannotExpand(AccountingItem item) {
        return item.getBusinessGroup().equals("ALL");
    }

}
