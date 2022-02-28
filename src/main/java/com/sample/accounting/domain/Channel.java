package com.sample.accounting.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sample.accounting.domain.accounting.AccountingItem;
import com.sample.accounting.domain.accounting.AccountingItemUtils;
import com.sample.accounting.domain.accounting.BaseAccounting;
import com.sample.accounting.repository.AccountingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("channel")
public class Channel implements BaseAccounting {

    @Autowired
    private AccountingRepository repository;

    @Override
    public List<AccountingItem> accountForProduct(List<AccountingItem> items) {
        List<String> includeCondition = List.of("businessGroup", "businessUnit");
        List<String> excludeCondition = List.of("channel", "market");
        return account(items, includeCondition, excludeCondition);
    }

    @Override
    public List<AccountingItem> accountForChannel(List<AccountingItem> items) {
        List<String> includeCondition = List.of();
        List<String> excludeCondition = List.of("channel", "market");
        return account(items, includeCondition, excludeCondition);
    }

    private List<AccountingItem> account(List<AccountingItem> items, List<String> includeCondition,
            List<String> excludeCondition) {
        List<AccountingItem> toExpandItems = items.stream().filter(item -> "ALL".equals(item.getMarket()))
                .collect(Collectors.toList());
        List<AccountingItem> noChangeItems = items.stream().filter(item -> !"ALL".equals(item.getMarket()))
                .collect(Collectors.toList());

        List<AccountingItem> expandedItems = new ArrayList<AccountingItem>();
        toExpandItems.forEach(item -> {
            expandedItems.addAll(expendedItem(item, includeCondition, excludeCondition));
        });

        return Stream.concat(noChangeItems.stream(), expandedItems.stream()).collect(Collectors.toList());
    }

    private List<AccountingItem> expendedItem(AccountingItem item, List<String> includeCondition,
            List<String> excludeCondition) {
        Map<AccountingItem, Double> percentage = calculatePercentage(item, includeCondition, excludeCondition);
        return AccountingItemUtils.prorate(item, percentage);
    }

    private Map<AccountingItem, Double> calculatePercentage(AccountingItem baseItem, List<String> includeCondition,
            List<String> excludeCondition) {
        List<AccountingItem> items = repository.load(baseItem, includeCondition, excludeCondition);
        double total = items.stream().mapToDouble(item -> item.getAmount()).sum();
        return items.stream().collect(Collectors.toMap(item -> item, item -> item.getAmount() / total));
    }

}
