package stockhelper.main;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class AssetsBracketsCalculatorImpl implements AssetsBracketsCalculator {
    public static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("PST", ZoneId.SHORT_IDS);

    @Override
    public Map<LocalDate, List<InvestmentLine>> calculate(List<Transaction> transactions) {
        Map<LocalDate, List<Transaction>> transactionsPerDay = new HashMap<>();
        for (Transaction transaction : transactions) {
            transactionsPerDay.computeIfAbsent(LocalDate.ofInstant(transaction.getDate(), DEFAULT_ZONE_ID), __ -> new ArrayList<>())
                    .add(transaction);
        }

        List<LocalDate> allDates = new ArrayList<>(transactionsPerDay.keySet());
        Collections.sort(allDates);

        Map<LocalDate, List<InvestmentLine>> portfolioPerDay = new HashMap<>();
        for (LocalDate date : allDates) {
            Map<Key, Integer> totalPerAccountTicket = new HashMap<>();
            for (Transaction transaction : transactionsPerDay.get(date)) {
                final int qty;
                if (transaction.getOperation() == TransactionOperation.BUY) {
                    qty = transaction.getQuantity();
                } else {
                    qty = -transaction.getQuantity();
                }
                totalPerAccountTicket.merge(
                        new Key(transaction.getAccount(), transaction.getTicket()),
                        qty,
                        Integer::sum);
            }
            List<InvestmentLine> lines = new ArrayList<>();
            for (Map.Entry<Key, Integer> entry : totalPerAccountTicket.entrySet()) {
                String ticket = entry.getKey().getTicket();
                int quantity = entry.getValue();
                String account = entry.getKey().getAccount();
                InvestmentLine line = new InvestmentLine(ticket, quantity, account);
                lines.add(line);
            }
            portfolioPerDay.put(date, lines);
        }

        Map<LocalDate, List<InvestmentLine>> result = new HashMap<>();

        return null;
    }


    @Data
    @AllArgsConstructor
    static class Key {
        private String account;
        private String ticket;

    }

}
