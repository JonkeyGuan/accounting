package com.sample.split.domain.accounting;

import java.util.List;

public interface BaseAccounting {

    public String name();

    public List<AccountingItem> accountForProduct(List<AccountingItem> items);

    public List<AccountingItem> accountForChannel(List<AccountingItem> items);
    
}
