package com.sample.accounting.domain.accounting;

import java.util.List;

public interface BaseAccounting {

    public List<AccountingItem> accountForProduct(List<AccountingItem> items);

    public List<AccountingItem> accountForChannel(List<AccountingItem> items);
    
}
