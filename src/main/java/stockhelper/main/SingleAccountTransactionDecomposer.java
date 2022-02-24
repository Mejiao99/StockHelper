package stockhelper.main;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        Set<String> fromTickets = new HashSet<>();
        Set<String> toTickets = new HashSet<>();
        for (InvestmentLine line : from) {
            fromTickets.add(line.getTicket());
        }
        for (InvestmentLine line : to) {
            toTickets.add(line.getTicket());
        }
        Set<String> toBuy = setOperationRest(toTickets, fromTickets);
        String ticket = String.valueOf(toBuy.stream().findFirst().get());

        InvestmentLine investmentLine = to.get(0);
        int quantity = investmentLine.getQuantity();
        String account = investmentLine.getAccount();
        Transaction transaction = new Transaction(ticket, quantity, account, TransactionOperation.BUY);
        List<Transaction> result = new ArrayList<>();

        result.add(transaction);

        return result;
    }

    private Set<String> setOperationRest(final Set<String> to, final Set<String> from) {
        Set<String> result = new HashSet<>(to);
        result.removeIf(from::contains);
        return result;
    }

}
