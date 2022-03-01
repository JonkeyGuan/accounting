package com.sample.accounting.domain.accounting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sample.accounting.repository.AccountingRepository;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AccountingItemAccounting implements BaseAccounting {

    @Autowired
    private AccountingRepository repository;

    protected List<AccountingItem> account(List<AccountingItem> items, String nodeName, List<String> includeCondition,
            List<String> excludeCondition) {
        List<AccountingItem> toExpandItems = items.stream()
                .filter(item -> "ALL".equals(AccountingItemUtils.getValue(item, nodeName)))
                .collect(Collectors.toList());
        List<AccountingItem> noChangeItems = items.stream()
                .filter(item -> !"ALL".equals((AccountingItemUtils.getValue(item, nodeName))))
                .collect(Collectors.toList());

        List<AccountingItem> expandedItems = new ArrayList<AccountingItem>();
        toExpandItems.forEach(item -> {
            expandedItems.addAll(expandedItem(item, includeCondition, excludeCondition));
        });

        return Stream.concat(noChangeItems.stream(), expandedItems.stream()).collect(Collectors.toList());
    }

    protected abstract boolean cannotExpand(AccountingItem item);

    protected List<AccountingItem> expandedItem(AccountingItem item, List<String> includeCondition,
            List<String> excludeCondition) {
        if (cannotExpand(item)) {
            return List.of(item);
        }
        Map<AccountingItem, Double> percentage = calculatePercentage(item, includeCondition, excludeCondition);
        return AccountingItemUtils.prorate(item, percentage);
    }

    protected Map<AccountingItem, Double> calculatePercentage(AccountingItem baseItem, List<String> includeCondition,
            List<String> excludeCondition) {
        includeCondition = includeCondition.stream().filter(i -> !"ignore".equals(i)).collect(Collectors.toList());
        excludeCondition = excludeCondition.stream().filter(i -> !"ignore".equals(i)).collect(Collectors.toList());

        List<AccountingItem> items = repository.load(baseItem, includeCondition, excludeCondition);
        double total = items.stream().mapToDouble(item -> item.getAmount()).sum();
        return items.stream().collect(Collectors.toMap(item -> item, item -> item.getAmount() / total));
    }
}
