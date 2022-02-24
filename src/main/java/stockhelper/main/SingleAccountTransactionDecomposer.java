package stockhelper.main;


import java.util.List;

// Ejemplo:
// Inversiones actuales = A:10:C1, B:20:C1
// Lista de lo que quiero = A:20:C1, C:10:C2
// Salida: A:10:C1:COMPRAR, B:20:C1:VENDER, C:10:C2:COMPRAR

public class SingleAccountTransactionDecomposer implements TransactionDecomposer {
    @Override
    public List<Transaction> decompose(List<InvestmentLine> from, List<InvestmentLine> to) {
        return null;
    }
}
