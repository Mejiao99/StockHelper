package stockhelper.main;


import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ToString
public class SingleAccountTransactionDecomposer implements TransactionDecomposer {
    @Override
    public List<Transaction> decompose(final List<InvestmentLine> from, final List<InvestmentLine> to) {
        final Set<String> allTickets = new HashSet<>();
        allTickets.addAll(getTicketsFromInvestments(from));
        allTickets.addAll(getTicketsFromInvestments(to));

        List<Transaction> result = new ArrayList<>();
        for (String ticket : allTickets) {
            String account = getAccountPerLists(from, to, ticket);
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

    private String getAccountPerLists(final List<InvestmentLine> listA, final List<InvestmentLine> listB, final String ticket) {
        if (getAccountPerTicket(listA, ticket) != null) {
            return getAccountPerTicket(listA, ticket);
        } else if (getAccountPerTicket(listB, ticket) != null) {
            return getAccountPerTicket(listB, ticket);
        }
        return null;
    }

    private String getAccountPerTicket(final List<InvestmentLine> investments, final String ticket) {
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

    private int getQuantityPerTicket(final List<InvestmentLine> investments, final String ticket) {
        for (InvestmentLine line : investments) {
            if (line.getTicket().equals(ticket)) {
                return line.getQuantity();
            }
        }
        return 0;
    }

    private Set<String> getTicketsFromInvestments(final List<InvestmentLine> investments) {
        Set<String> result = new HashSet<>();
        for (InvestmentLine line : investments) {
            result.add(line.getTicket());
        }
        return result;
    }
}
