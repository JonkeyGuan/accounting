package com.sample.split.domain;

import java.util.List;

import com.sample.split.domain.accounting.AccountingItem;
import com.sample.split.domain.accounting.BaseAccounting;

import org.springframework.stereotype.Service;

@Service
public class Buyer implements BaseAccounting {

    @Override
    public List<AccountingItem> accountForChannel(List<AccountingItem> items) {
        return items;
    }

    @Override
    public String name() {
        return "buyer";
    }

    @Override
    public List<AccountingItem> accountForProduct(List<AccountingItem> items) {
        return items;
    }
}
