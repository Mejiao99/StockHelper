package stockhelper.main;


import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Ejemplo:
// Inversiones actuales = A:10:C1, B:20:C1
// Lista de lo que quiero = A:20:C1, C:10:C2
// Salida: A:10:C1:COMPRAR, B:20:C1:VENDER, C:10:C2:COMPRAR

@ToString
public class SingleAccountTransactionDecomposer implements TransactionDecomposer {
    @Override
    public List<Transaction> decompose(List<InvestmentLine> from, List<InvestmentLine> to) {
        //TODO: ITERATE fromTicks + toTickets
        Set<String> fromTickets = getTicketsFromInvestments(from);
        Set<String> toTickets = getTicketsFromInvestments(to);
        // After this line fromTickets equals to allTickets.
        fromTickets.addAll(toTickets);
        Set<String> allTickets = fromTickets;


        List<Transaction> result = new ArrayList<>();

//        Set<String> toChange = intersectionSet(fromTickets, toTickets);
        for (String ticket : allTickets) {
            InvestmentLine investmentTo = getInvestmentLine(to, ticket);
            InvestmentLine investmentFrom = getInvestmentLine(from, ticket);

            int quantityTo = 0;
            if (investmentTo != null) {
                quantityTo = investmentTo.getQuantity();
            }
            int quantityFrom = 0;
            if (investmentFrom != null) {
                quantityFrom = investmentFrom.getQuantity();
            }

            int quantity = 0;
            TransactionOperation transactionOperation = null;
            if (quantityFrom > quantityTo) {
                quantity = quantityFrom - quantityTo;
                transactionOperation = TransactionOperation.SELL;
            }
            if (quantityFrom < quantityTo) {
                quantity = quantityTo - quantityFrom;
                transactionOperation = transactionOperation.BUY;
            }
            if (quantityFrom == quantityTo) {
                continue;
            }
            String account = "";
            if (investmentTo != null) {
                account = investmentTo.getAccount();
            }
            if (investmentFrom != null) {
                account = investmentFrom.getAccount();
            }
            result.add(new Transaction(ticket, quantity, account, transactionOperation));
        }
        return result;
    }

    private Set<String> intersectionSet(Set<String> setA, Set<String> setB) {
        Set<String> result = new HashSet<>(setA);
        result.retainAll(setB);
        return result;
    }

    private InvestmentLine getInvestmentLine(List<InvestmentLine> investments, String ticket) {

        for (InvestmentLine line : investments) {
            if (line.getTicket().equals(ticket)) {
                System.err.println(line);
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
