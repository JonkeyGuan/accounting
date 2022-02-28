package com.sample.accounting.domain.accounting;

import java.util.ArrayList;
import java.util.List;

import com.sample.accounting.repository.AccountingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;

@Service
@Log
public class Accounting {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    private AccountingRepository repository;

    public void accountingByProduct() {
        List<AccountingItem> items = repository.load();
        // log.info("raw data: " + items);
        List<BaseAccounting> path = assembleAccountingPath(
                List.of("workHours", "workHoursNoneBG", "accountingMerge", "buyer", "market", "channel"));
        for (BaseAccounting node : path) {
            List<AccountingItem> expandItems = node.accountForProduct(items);
            items = expandItems;
            // log.info("after " + node.name() + " account: " + items);
        }
        log.info("product: " + items);
    }

    public void accountingByChannel() {
        List<AccountingItem> items = repository.load();
        // log.info("raw data: " + items);
        List<BaseAccounting> path = assembleAccountingPath(List.of("buyer", "market", "channel"));
        for (BaseAccounting node : path) {
            List<AccountingItem> expandItems = node.accountForChannel(items);
            items = expandItems;
            // log.info("after " + node.name() + " account: " + items);
        }
        log.info("channel: " + items);
    }

    private List<BaseAccounting> assembleAccountingPath(List<String> path) {
        List<BaseAccounting> result = new ArrayList<>();
        path.forEach(item -> {
            BaseAccounting accounting = applicationContext.getBean(item, BaseAccounting.class);
            result.add(accounting);
        });
        return result;
    }

}
