package stockhelper.main;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AssetsBracketsCalculatorImpl implements AssetsBracketsCalculator {
    @Override
    public Map<LocalDate, List<InvestmentLine>> calculate(List<Transaction> transactions) {
        Map<LocalDate, List<InvestmentLine>> result = new HashMap<>();
        for (Transaction transaction : transactions) {
            LocalDate date = LocalDate.ofInstant(transaction.getDate(), ZoneOffset.UTC);
            List<InvestmentLine> investments = getInvestments(transactions, date);
            result.put(date, investments);
        }
        return result;
    }

    private Set<List<InvestmentLine>> investments(List<Transaction> transactions) {
        Set<List<InvestmentLine>> lines = new HashSet<>();
        for (Transaction transaction : transactions) {
            LocalDate date = LocalDate.ofInstant(transaction.getDate(), ZoneOffset.UTC);
            List<InvestmentLine> investments = getInvestments(transactions, date);
            lines.add(investments);
        }
        return lines;
    }

    private List<InvestmentLine> getInvestments(List<Transaction> transactions, LocalDate date) {
        List<InvestmentLine> investments = new ArrayList<>();
        for (Transaction transaction : transactions) {
            LocalDate transactionDate = LocalDate.ofInstant(transaction.getDate(), ZoneOffset.UTC);
            if (transactionDate == date) {
                InvestmentLine line = new InvestmentLine(transaction.getTicket(), transaction.getQuantity(), transaction.getAccount());
                investments.add(line);
            }
            return investments;
        }
        return Collections.emptyList();
    }
}
