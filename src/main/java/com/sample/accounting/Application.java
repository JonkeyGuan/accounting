package com.sample.accounting;

// import com.sample.accounting.gateway.RulesGateway;
import com.sample.accounting.resource.AccountingResource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner{

    @Autowired
    AccountingResource resource;

    // @Autowired
    // RulesGateway rulesGateway;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        // resource.accountingByProduct();
        resource.accountingByChannel();
        // rulesGateway.test();
    }

}
