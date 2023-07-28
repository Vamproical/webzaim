package com.mimoun.webzaim.service;

import com.mimoun.webzaim.model.Credit;
import com.mimoun.webzaim.model.PaymentDictionary;
import com.mimoun.webzaim.repository.CreditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreditService {
    private final CreditRepository creditRepository;

    public List<Credit> findAllBySort(Sort sort) {
        return creditRepository.findAll(sort);
    }

    public String getThroughLine() {
        List<Credit> credits = findAllBySort(Sort.by(Sort.Direction.ASC, "firstPaymentDate"));

        Map<LocalDate, PaymentDictionary> throughLineMap = initThroughLineMap(credits);

        for (Credit credit : credits) {
            char[] paymentArray = credit.getPayment().toCharArray();
            LocalDate firstPaymentDate = credit.getFirstPaymentDate();

            for (int i = paymentArray.length - 1; i >= 0; i--) {
                PaymentDictionary value = throughLineMap.get(firstPaymentDate);
                PaymentDictionary paymentValue = PaymentDictionary.findByValue(String.valueOf(paymentArray[i]));

                if (paymentValue.getWeight() > value.getWeight()) {
                    throughLineMap.put(firstPaymentDate, paymentValue);
                }

                firstPaymentDate = firstPaymentDate.plusMonths(1);
            }
        }

        String throughLine = reverseAndGetValues(throughLineMap.values());
        log.info("Through line is {}", throughLine);
        return throughLine;
    }

    private String reverseAndGetValues(Collection<PaymentDictionary> values) {
        StringBuilder string = new StringBuilder();

        values.stream()
              .map(PaymentDictionary::getValue)
              .forEach(string::append);

        return string.reverse()
                     .toString();
    }

    private Map<LocalDate, PaymentDictionary> initThroughLineMap(List<Credit> credits) {
        int size = credits.size();
        LocalDate minDate = credits.get(0).getFirstPaymentDate();
        LocalDate maxDate = credits.get(size - 1).getFirstPaymentDate();
        int countLastPaymentMonth = credits.get(size - 1).getPayment().length();
        int countAllMonthPeriod = Period.between(minDate, maxDate).getMonths() + countLastPaymentMonth;

        Map<LocalDate, PaymentDictionary> characters = new TreeMap<>();
        for (int i = 0; i < countAllMonthPeriod; i++) {
            characters.put(minDate, PaymentDictionary.X);
            minDate = minDate.plusMonths(1);
        }

        return characters;
    }
}
