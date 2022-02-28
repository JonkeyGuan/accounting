package com.sample.split.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sample.split.domain.accounting.AccountingItem;
import com.sample.split.domain.accounting.AccountingItemUtils;
import com.sample.split.domain.accounting.BaseAccounting;
import com.sample.split.repository.AccountingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Market implements BaseAccounting {

    @Autowired
    private AccountingRepository repository;

    @Override
    public String name() {
        return "market";
    }

    @Override
    public List<AccountingItem> accountForProduct(List<AccountingItem> items) {
        return account(items);
    }

    @Override
    public List<AccountingItem> accountForChannel(List<AccountingItem> items) {
        return account(items);
    }

    private List<AccountingItem> account(List<AccountingItem> items) {
        List<AccountingItem> toExpandItems = items.stream().filter(item -> "ALL".equals(item.getMarket()))
                .collect(Collectors.toList());
        List<AccountingItem> noChangeItems = items.stream().filter(item -> !"ALL".equals(item.getMarket()))
                .collect(Collectors.toList());

        List<AccountingItem> expandedItems = new ArrayList<AccountingItem>();
        toExpandItems.forEach(item -> {
            expandedItems.addAll(expandedItem(item));
        });

        return Stream.concat(noChangeItems.stream(), expandedItems.stream()).collect(Collectors.toList());
    }

    private List<AccountingItem> expandedItem(AccountingItem item) {
        if (item.getChannel().equals("ALL")) {
            return List.of(item);
        }

        Map<AccountingItem, Double> percentage = calculatePercentage(item);
        return AccountingItemUtils.prorate(item, percentage);
    }

    private Map<AccountingItem, Double> calculatePercentage(AccountingItem baseItem) {
        List<String> includeCondition = List.of("channel", "businessGroup", "businessUnit");
        List<String> excludeCondition = List.of("market");
        List<AccountingItem> items = repository.load(baseItem, includeCondition, excludeCondition);
        double total = items.stream().mapToDouble(item -> item.getAmount()).sum();
        return items.stream().collect(Collectors.toMap(item -> item, item -> item.getAmount() / total));
    }

}
