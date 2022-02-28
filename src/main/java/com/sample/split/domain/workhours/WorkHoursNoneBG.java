package com.sample.split.domain.workhours;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sample.split.domain.accounting.AccountingItem;
import com.sample.split.domain.accounting.BaseAccounting;
import com.sample.split.repository.WorkHoursRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkHoursNoneBG implements BaseAccounting {

    @Autowired
    private WorkHoursRepository repository;

    @Override
    public String name() {
        return "workHoursNoneBG";
    }

    @Override
    public List<AccountingItem> accountForChannel(List<AccountingItem> items) {
        return items;
    }

    @Override
    public List<AccountingItem> accountForProduct(List<AccountingItem> items) {
        List<AccountingItem> toExpandItems = items.stream().filter(item -> "none-bg".equals(item.getBusinessGroup()))
                .collect(Collectors.toList());
        List<AccountingItem> noChangeItems = items.stream()
                .filter(item -> !"none-bg".equals(item.getBusinessGroup()))
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
        items = items.stream().filter(item -> !"none-bg".equals(item.getBusinessGroup())).collect(Collectors.toList());
        double total = items.stream().mapToDouble(item -> item.getHours()).sum();
        return items.stream().collect(Collectors.toMap(item -> item, item -> item.getHours() / total));
    }

}
