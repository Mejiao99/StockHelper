package stockhelper.main;


import jdk.dynalink.Operation;

import java.util.ArrayList;
import java.util.List;

// Ejemplo:
// Inversiones actuales = A:10:C1, B:20:C1
// Lista de lo que quiero = A:20:C1, C:10:C2
// Salida: A:10:C1:COMPRAR, B:20:C1:VENDER, C:10:C2:COMPRAR

public class SingleAccountTransactionDecomposer implements TransactionDecomposer {
    @Override
    public List<Transaction> decompose(List<InvestmentLine> from, List<InvestmentLine> to) {
        // Tengo una lista combinada de acciones entre el from y el to
        List<InvestmentLine> investmentCombined = new ArrayList<>();
        // Calculo la diferencia entre las listas y retorno una operacion
        TransactionOperation transactionOperation = calculateOperation(investmentCombined);

        Transaction transaction = new Transaction(ticket, quantity, account, transactionOperation);

        List<Transaction> result = new ArrayList<>();
        result.add(transaction);

        return result;
    }

    private TransactionOperation calculateOperation(List<InvestmentLine> stock) {
        TransactionOperation.values();
        return null;
    }
}
