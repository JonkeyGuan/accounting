package com.sample.accounting.domain;

import java.util.List;

import com.sample.accounting.domain.accounting.AccountingItem;
import com.sample.accounting.domain.accounting.BaseAccounting;

import org.springframework.stereotype.Component;

@Component("buyer")
public class Buyer implements BaseAccounting {

    @Override
    public List<AccountingItem> accountForChannel(List<AccountingItem> items) {
        return items;
    }

    @Override
    public List<AccountingItem> accountForProduct(List<AccountingItem> items) {
        return items;
    }
}
