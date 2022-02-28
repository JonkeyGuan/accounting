package com.sample.accounting.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.sample.accounting.domain.accounting.AccountingItem;
import com.sample.accounting.domain.accounting.AccountingItemUtils;

import org.springframework.stereotype.Repository;

@Repository
public class AccountingRepository {

    public List<AccountingItem> load() {
        return simulate();
    }

    private List<AccountingItem> simulate() {
        List<AccountingItem> result = new ArrayList<>();
        result.add(new AccountingItem("202108", "ALL", "ALL", "ALL", "ALL", "ALL", "ALL", "管理费用_办公快递费", "工时占比", 10000));
        result.add(new AccountingItem("202108", "OSO", "ALL", "ALL", "Charging", "Cable", "A7114", "order_revenue", "order_revenue", 4000));
        result.add(new AccountingItem("202108", "OSO", "AliExpress", "ALL", "Charging", "Cable", "A7116", "order_revenue", "order_revenue", 5000));
        result.add(new AccountingItem("202108", "OSO", "amozon", "ALL", "Charging", "Cable", "A1010", "order_revenue", "order_revenue", 1000));
        result.add(new AccountingItem("202108", "OSO", "amozon", "ALL", "Charging", "Battery", "A1100", "order_revenue", "order_revenue", 3000));
        result.add(new AccountingItem("202108", "OSO", "AliExpress", "ALL", "Charging", "Battery", "A1103", "order_revenue", "order_revenue", 2000));
        result.add(new AccountingItem("202108", "OSO", "AliExpress", "ALL", "Headphone", "Headphone", "A3025", "order_revenue", "order_revenue", 1000));
        return result;
    }

    public List<AccountingItem> load(AccountingItem baseItem, List<String> includeCondition, List<String> excludeCondition) {
        List<AccountingItem> items = load();
        String baseIncludeKey = AccountingItemUtils.getGroupKey(baseItem, includeCondition);
        items = items.stream()
                .filter(item -> baseIncludeKey.equals(AccountingItemUtils.getGroupKey(item, includeCondition)))
                .collect(Collectors.toList());
        
        for(String condition : excludeCondition) {
            items = items.stream()
            .filter(item -> !"ALL".equals(AccountingItemUtils.getValue(item, condition)))
            .collect(Collectors.toList());
        }
        return items;
    }
}
