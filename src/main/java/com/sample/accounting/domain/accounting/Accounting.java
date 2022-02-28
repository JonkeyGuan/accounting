package com.sample.accounting.domain.accounting;

import java.util.List;

import com.sample.accounting.domain.Buyer;
import com.sample.accounting.domain.Channel;
import com.sample.accounting.domain.Market;
import com.sample.accounting.domain.workhours.WorkHours;
import com.sample.accounting.domain.workhours.WorkHoursNoneBG;
import com.sample.accounting.repository.AccountingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;

@Service
@Log
public class Accounting {

    @Autowired
    private AccountingRepository repository;

    @Autowired
    private WorkHours workHours;

    @Autowired
    private WorkHoursNoneBG workHoursNoneBG;

    @Autowired
    private AccountingMerge accountingMerge;

    @Autowired
    private Channel channel;

    @Autowired
    private Market market;

    @Autowired
    private Buyer buyer;

    public void accountingByProduct() {
        List<AccountingItem> items = repository.load();
        log.info("raw data: " + items);
        List<BaseAccounting> path = List.of(workHours, workHoursNoneBG, accountingMerge, buyer, market, channel);
        for (BaseAccounting node : path) {
            List<AccountingItem> expandItems = node.accountForProduct(items);
            items = expandItems;
            log.info("after " + node.name() + " account: " + items);
        }
    }

    public void accountingByChannel() {
        List<AccountingItem> items = repository.load();
        log.info("raw data: " + items);
        List<BaseAccounting> path = List.of(buyer, market, channel);
        for (BaseAccounting node : path) {
            List<AccountingItem> expandItems = node.accountForChannel(items);
            items = expandItems;
            log.info("after " + node.name() + " account: " + items);
        }
    }

}
