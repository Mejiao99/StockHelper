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
        Set<String> fromTickets = getTicketsFromInvestments(from);
        Set<String> toTickets = getTicketsFromInvestments(to);

        List<Transaction> result = new ArrayList<>();

        // Handle stocks to buy
        Set<String> toBuy = subtractSet(toTickets, fromTickets);
        for (String ticket : toBuy) {
            InvestmentLine investmentLine = getInvestmentLine(to, ticket);
            int quantity = investmentLine.getQuantity();
            String account = investmentLine.getAccount();
            result.add(new Transaction(ticket, quantity, account, TransactionOperation.BUY));
        }

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
