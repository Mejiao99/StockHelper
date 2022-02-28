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

            String account = "";
            if (getAccountPerTicket(from, ticket) != null) {
                account = getAccountPerTicket(from, ticket);
            } else if (getAccountPerTicket(to, ticket) != null) {
                account = getAccountPerTicket(to, ticket);
            }

            int difference = getQuantityPerTicket(from, ticket) - getQuantityPerTicket(to, ticket);
            if (difference == 0) {
            } else if (difference > 0) {
                result.add(new Transaction(ticket, difference, account, TransactionOperation.SELL));
            } else {
                result.add(new Transaction(ticket, -difference, account, TransactionOperation.BUY));
            }
        }
        return result;
    }

    private String getAccountPerTicket(List<InvestmentLine> investments, String ticket) {
        if (investments == null || investments.isEmpty()) {
            return null;
        }
        for (InvestmentLine line : investments) {
            if (line.getTicket().equals(ticket)) {
                return line.getAccount();
            }
        }
        return null;
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
