package stockhelper.main;

import java.util.List;

// TODO: add validations more stocks, more accounts
// ¿Qué transacciones tengo que hacer para llegar a lo esperado?
// Dos tipos de transacción vender o comprar ¿Qué cuénta, qué ticket, cuánto? ¿Vender o comprar?
// Entradas: 1. Lista de inversiones 2. Lista de lo que quiero
// Salida: Lista de transacciones (nueva clase)
// Cuenta 1 -> accion A -> Vender 10
// Ejemplo:
// Inversiones actuales = A:10:C1, B:20:C1
// Lista de lo que quiero = A:20:C1, C:10:C2
// Salida: A:10:C1:COMPRAR, B:20:C1:VENDER, C:10:C2:COMPRAR
public interface TransactionDecomposer {
    List<Transaction> decompose(List<InvestmentLine> from, List<InvestmentLine> to);
}
