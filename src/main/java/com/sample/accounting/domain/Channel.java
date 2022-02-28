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
import org.springframework.stereotype.Service;

@Service
public class Channel implements BaseAccounting {

    @Autowired
    private AccountingRepository repository;

    @Override
    public String name() {
        return "channel";
    }

    @Override
    public List<AccountingItem> accountForProduct(List<AccountingItem> items) {
        return account(items, List.of("businessGroup", "businessUnit"));
    }

    @Override
    public List<AccountingItem> accountForChannel(List<AccountingItem> items) {
        return account(items, List.of());
    }

    private List<AccountingItem> account(List<AccountingItem> items, List<String> includeCondition) {
        List<AccountingItem> toExpandItems = items.stream().filter(item -> "ALL".equals(item.getMarket()))
                .collect(Collectors.toList());
        List<AccountingItem> noChangeItems = items.stream().filter(item -> !"ALL".equals(item.getMarket()))
                .collect(Collectors.toList());

        List<AccountingItem> expandedItems = new ArrayList<AccountingItem>();
        toExpandItems.forEach(item -> {
            expandedItems.addAll(expendedItem(item, includeCondition));
        });

        return Stream.concat(noChangeItems.stream(), expandedItems.stream()).collect(Collectors.toList());
    }

    private List<AccountingItem> expendedItem(AccountingItem item, List<String> includeCondition) {
        Map<AccountingItem, Double> percentage = calculatePercentage(item, includeCondition);
        return AccountingItemUtils.prorate(item, percentage);
    }

    private Map<AccountingItem, Double> calculatePercentage(AccountingItem baseItem, List<String> includeCondition) {
        List<String> excludeCondition = List.of("channel", "market");
        List<AccountingItem> items = repository.load(baseItem, includeCondition, excludeCondition);
        double total = items.stream().mapToDouble(item -> item.getAmount()).sum();
        return items.stream().collect(Collectors.toMap(item -> item, item -> item.getAmount() / total));
    }

}
