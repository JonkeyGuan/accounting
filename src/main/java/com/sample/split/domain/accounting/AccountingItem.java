package com.sample.split.domain.accounting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class AccountingItem {

    private String month;
    private String channel;
    private String market;
    private String buyer;
    private String businessGroup;
    private String businessUnit;
    private String product;
    private String subCatalog;
    private String catalog;
    private double amount;

    @Override
    public String toString() {
        return "\nAccountingItem," + month + "," + channel + "," + market + "," + buyer + "," + businessGroup + ","
                + businessUnit + "," + product + "," + subCatalog + "," + catalog + "," + amount;
    }

}
