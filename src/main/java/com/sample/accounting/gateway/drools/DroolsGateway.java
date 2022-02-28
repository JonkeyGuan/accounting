package com.sample.accounting.gateway.drools;

import com.sample.accounting.gateway.RulesGateway;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DroolsGateway implements RulesGateway {

    @Autowired
    private KieContainer kieContainer;

    @Override
    public void test() {
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert("hello");
        kieSession.fireAllRules();
        kieSession.dispose();
    }

}
