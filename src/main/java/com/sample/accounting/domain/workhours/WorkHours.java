package com.sample.accounting.domain.workhours;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sample.accounting.domain.accounting.AccountingItem;
import com.sample.accounting.domain.accounting.BaseAccounting;
import com.sample.accounting.repository.WorkHoursRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkHours implements BaseAccounting {

    @Autowired
    private WorkHoursRepository repository;

    @Override
    public String name() {
        return "workHours";
    }

    @Override
    public List<AccountingItem> accountForChannel(List<AccountingItem> items) {
        return items;
    }

    @Override
    public List<AccountingItem> accountForProduct(List<AccountingItem> items) {
        List<AccountingItem> toExpandItems = items.stream().filter(item -> "工时占比".equals(item.getCatalog()))
                .collect(Collectors.toList());
        List<AccountingItem> noChangeItems = items.stream()
                .filter(item -> !"工时占比".equals(item.getCatalog()))
                .collect(Collectors.toList());

        List<AccountingItem> expandedItems = new ArrayList<AccountingItem>();
        toExpandItems.forEach(item -> {
            expandedItems.addAll(expandedItem(item));
        });

        return Stream.concat(noChangeItems.stream(), expandedItems.stream()).collect(Collectors.toList());
    }

    private List<AccountingItem> expandedItem(AccountingItem item) {
        Map<WorkHoursItem, Double> percentage = calculatePercentage();
        return WorkHoursItemUtils.prorate(item, percentage);
    }

    private Map<WorkHoursItem, Double> calculatePercentage() {
        List<WorkHoursItem> items = repository.load();
        double total = items.stream().mapToDouble(item -> item.getHours()).sum();
        return items.stream().collect(Collectors.toMap(item -> item, item -> item.getHours() / total));
    }

}
