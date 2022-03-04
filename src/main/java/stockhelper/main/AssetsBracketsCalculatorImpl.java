package stockhelper.main;

import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@AllArgsConstructor
public class AssetsBracketsCalculatorImpl implements AssetsBracketsCalculator {
    public static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("PST", ZoneId.SHORT_IDS);

    @Override
    public Map<LocalDate, List<InvestmentLine>> calculate(List<Transaction> transactions) {
        Set<LocalDate> transactionsDates = getDates(transactions);

        final Map<LocalDate, Map<String, Map<String, InvestmentLine>>> investmentsPerDay = new HashMap<>();
        for (LocalDate date : transactionsDates) {
            List<Transaction> transactionsPerDay = getTransactionsPerDate(transactions, date);
            System.err.println("getTransactionsPerDate: " + transactionsPerDay);
            Set<String> accounts = getAccounts(transactionsPerDay);
            for (String account : accounts) {
                List<Transaction> accountTransactions = getAccountTransactions(transactionsPerDay, account);
                Set<String> tickets = getTickets(accountTransactions);
                for (String ticket : tickets) {
                    List<Transaction> transactionsPerTicket = getTransactionsPerTicket(accountTransactions, ticket);
                    for (Transaction transaction : transactionsPerTicket) {
                        investmentsPerDay.computeIfAbsent(date, __ -> new HashMap<>())
                                .computeIfAbsent(account, __ -> new HashMap<>())
                                .merge(ticket, new InvestmentLine(ticket, transaction.getQuantity(), account),
                                        (oldLine, newLine) ->
                                                new InvestmentLine(ticket, (oldLine.getQuantity() + newLine.getQuantity()), account));
                    }
                }
            }


        }
        Map<LocalDate, List<InvestmentLine>> result = new HashMap<>();
        investmentsPerDay.forEach((date, accountsMap) -> {
            accountsMap.forEach((account, ticketsMap) -> {
                result.computeIfAbsent(date, __ -> new ArrayList<>()).addAll(ticketsMap.values());
            });
        });
        System.err.println(result);
        return result;
    }

    private Set<String> getAccounts(List<Transaction> transactions) {
        return transactions.stream()
                .map(Transaction::getAccount)
                .collect(Collectors.toSet());
    }

    private Set<String> getTickets(List<Transaction> transactions) {
        return transactions.stream()
                .map(Transaction::getTicket)
                .collect(Collectors.toSet());
    }


    private List<Transaction> getTransactionsPerDate(List<Transaction> transactions, LocalDate date) {
        List<Transaction> results = new ArrayList<>();
        for (Transaction transaction : transactions) {
            LocalDate trxDate = LocalDate.ofInstant(transaction.getDate(), DEFAULT_ZONE_ID);
            if (trxDate.equals(date)) {
                Transaction trx = new Transaction(transaction.getTicket(),
                        transaction.getQuantity(),
                        transaction.getAccount(),
                        transaction.getOperation(),
                        transaction.getDate());
                results.add(trx);
            }
        }
        return results;
    }


    private List<Transaction> getAccountTransactions(List<Transaction> transactions, String account) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getAccount().equals(account)) {
                result.add(transaction);
            }
        }
        return result;
    }

    private List<Transaction> getTransactionsPerTicket(List<Transaction> transactions, String ticket) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getTicket().equals(ticket)) {
                result.add(transaction);
            }
        }
        return result;
    }

    InvestmentLine reduceTransactions(InvestmentLine line, List<Transaction> transactions) {
        int qty = line.getQuantity();
        for (Transaction transaction : transactions) {
            if (transaction.getOperation() == TransactionOperation.BUY) {
                qty = qty + transaction.getQuantity();
            }
            if (transaction.getOperation() == TransactionOperation.SELL) {
                qty = qty - transaction.getQuantity();
            }
        }
        return (new InvestmentLine(line.getTicket(), qty, line.getAccount()));
    }


    private Set<LocalDate> getDates(List<Transaction> transactions) {
        return transactions.stream()
                .map(Transaction::getDate)
                .map(instant -> LocalDate.ofInstant(instant, DEFAULT_ZONE_ID))
                .collect(Collectors.toCollection(TreeSet::new));
    }


}
