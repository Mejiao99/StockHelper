package stockhelper.main;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Ejemplo:
// Inversiones actuales = A:10:C1, B:20:C1
// Lista de lo que quiero = A:20:C1, C:10:C2
// Salida: A:10:C1:COMPRAR, B:20:C1:VENDER, C:10:C2:COMPRAR


//Ten en cuenta esto para mañana. Los conjuntos (matemáticamente y en Java) tienen operacione de adicion y resta.
//        {1,2} + {2,3} = {1,2,3}
//        {1,2} - {2,3} = {1}
public class SingleAccountTransactionDecomposer implements TransactionDecomposer {
    @Override
    public List<Transaction> decompose(List<InvestmentLine> from, List<InvestmentLine> to) {
        Set<String> fromTickets = getTicketsFromInvestmentList(from);
        Set<String> toTickets = getTicketsFromInvestmentList(to);

        Set<String> toBuy = setOperationRest(toTickets, fromTickets);
        String ticket = null;
        for (String buy : toBuy) {
            ticket = buy;
        }
        InvestmentLine investmentLine = getInvestmentLine(to, ticket);
        int quantity = investmentLine.getQuantity();
        String account = investmentLine.getAccount();
        Transaction transaction = new Transaction(ticket, quantity, account, TransactionOperation.BUY);
        List<Transaction> result = new ArrayList<>();

        result.add(transaction);

        return result;
    }

    private InvestmentLine getInvestmentLine(List<InvestmentLine> investmentLineList, String ticket) {
        for (InvestmentLine line : investmentLineList) {
            if (line.getTicket().equals(ticket)) {
                return line;
            }
        }
        return null;
    }

    private Set<String> getTicketsFromInvestmentList(List<InvestmentLine> from) {
        Set<String> result = new HashSet<>();
        for (InvestmentLine line : from) {
            result.add(line.getTicket());
        }
        return result;
    }

    private Set<String> setOperationRest(final Set<String> setA, final Set<String> setB) {
        Set<String> result = new HashSet<>(setA);
        result.removeAll(setB);
        return result;
    }

}
