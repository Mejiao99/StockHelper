package stockhelper.main;


import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ToString
public class SingleAccountTransactionDecomposer implements TransactionDecomposer {
    @Override
    public List<Transaction> decompose(List<InvestmentLine> from, List<InvestmentLine> to) {
        Set<String> fromTickets = getTicketsFromInvestments(from);
        Set<String> toTickets = getTicketsFromInvestments(to);
        // After this line fromTickets equals to allTickets.
        fromTickets.addAll(toTickets);

        List<Transaction> result = new ArrayList<>();
        Set<String> allTickets = fromTickets;
        for (String ticket : allTickets) {
            InvestmentLine investmentFrom = getInvestmentLine(from, ticket);
            InvestmentLine investmentTo = getInvestmentLine(to, ticket);

            int difference = getQuantityPerTicket(from, ticket) - getQuantityPerTicket(to, ticket);

            TransactionOperation transactionOperation = null;
            if (difference == 0) {
                continue;
            } else if (difference > 0) {
                transactionOperation = TransactionOperation.SELL;
            } else if (difference < 0) {
                difference = -difference;
                transactionOperation = transactionOperation.BUY;
            }
            String account = "";
            if (investmentTo != null) {
                account = investmentTo.getAccount();
            }
            if (investmentFrom != null) {
                account = investmentFrom.getAccount();
            }
            result.add(new Transaction(ticket, difference, account, transactionOperation));
        }
        return result;
    }

    private int getQuantityPerTicket(List<InvestmentLine> investments, String ticket) {
        for (InvestmentLine line : investments) {
            if (line.getTicket().equals(ticket)) {
                return line.getQuantity();
            }
        }
        return 0;
    }

    private Set<String> intersectionSet(Set<String> setA, Set<String> setB) {
        Set<String> result = new HashSet<>(setA);
        result.retainAll(setB);
        return result;
    }

    private InvestmentLine getInvestmentLine(List<InvestmentLine> investments, String ticket) {
        for (InvestmentLine line : investments) {
            if (line.getTicket().equals(ticket)) {

                return line;
            }
        }
        return null;
    }

    private Set<String> getTicketsFromInvestments(List<InvestmentLine> investments) {
        Set<String> result = new HashSet<>();
        for (InvestmentLine line : investments) {
            result.add(line.getTicket());
        }
        return result;
    }

    private Set<String> subtractSet(final Set<String> setA, final Set<String> setB) {
        Set<String> result = new HashSet<>(setA);
        result.removeAll(setB);
        return result;
    }
}
