package com.mimoun.webzaim;

import com.mimoun.webzaim.service.CreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandLineAppStartupRunner implements CommandLineRunner {
    private final CreditService creditService;

    @Override
    public void run(String... args) {
        System.out.println(creditService.getThroughLine());
    }
}
