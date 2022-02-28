package com.sample.accounting.domain.workhours;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sample.accounting.domain.accounting.AccountingItem;

public class WorkHoursItemUtils {

    public static List<AccountingItem> prorate(AccountingItem item, Map<WorkHoursItem, Double> percentage) {
        List<AccountingItem> result = new ArrayList<AccountingItem>();
        percentage.forEach((k, v) -> {
            AccountingItem newItem = AccountingItem.builder()
                    .month(item.getMonth())
                    .channel(item.getChannel())
                    .market(item.getMarket())
                    .buyer(item.getBuyer())
                    .businessGroup(k.getBusinessGroup())
                    .businessUnit(k.getBusinessUnit())
                    .product(item.getProduct())
                    .subCatalog(item.getSubCatalog())
                    .catalog(item.getCatalog())
                    .amount((double) Math.round(item.getAmount() * v * 100) / 100)
                    .build();
            result.add(newItem);
        });
        return result;
    }
}