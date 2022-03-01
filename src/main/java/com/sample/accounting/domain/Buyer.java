package com.sample.accounting.domain;

import java.util.List;

import com.sample.accounting.domain.accounting.AccountingItem;
import com.sample.accounting.domain.accounting.AccountingItemAccounting;

import org.springframework.stereotype.Component;

@Component("buyer")
public class Buyer extends AccountingItemAccounting {

    @Override
    public List<AccountingItem> accountForProduct(List<AccountingItem> items) {
        // "channel", "market", "buyer", "businessGroup", "businessUnit", "product"
        List<String> includeCondition = List.of("channel", "market", "ignore", "businessGroup", "businessUnit", "ignore");
        List<String> excludeCondition = List.of("channel", "market", "ignore", "businessGroup", "businessUnit", "product");
        return account(items, "buyer", includeCondition, excludeCondition);
    }

    @Override
    public List<AccountingItem> accountForChannel(List<AccountingItem> items) {
        // "channel", "market", "buyer", "businessGroup", "businessUnit", "product"
        List<String> includeCondition = List.of("channel", "market", "ignore", "ignore", "ignore", "ignore");
        List<String> excludeCondition = List.of("channel", "market", "ignore", "ignore", "ignore", "ignore");
        return account(items, "buyer", includeCondition, excludeCondition);
    }

    @Override
    protected boolean cannotExpand(AccountingItem item) {
        return true;
    }
}
