package stockhelper.main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PerAccountTransactionDecomposerTest {
    private PerAccountTransactionDecomposer decomposer;

    @BeforeEach
    public void setup() {
        decomposer = new PerAccountTransactionDecomposer();
    }

    @Test
    public void buy_sell_few_stocks() {
        // Preparation
        List<InvestmentLine> fromAllocations = Arrays.asList(
                new InvestmentLine("A", 512, "x"),
                new InvestmentLine("B", 100, "x"),
                new InvestmentLine("A", 777, "y"),
                new InvestmentLine("A", 100, "w")
        );
        List<InvestmentLine> toAllocations = Arrays.asList(
                new InvestmentLine("A", 999, "x"),
                new InvestmentLine("A", 210, "z"),
                new InvestmentLine("A", 100, "w")
        );

        // Execution
        List<Transaction> transactionsList = decomposer.decompose(fromAllocations, toAllocations);

        // Validations
        assertNotNull(transactionsList);
        assertEquals(3, transactionsList.size());

        validateTransaction(find(transactionsList, "A"), "A", 487, "x", TransactionOperation.BUY);
        validateTransaction(find(transactionsList, "B"), "B", 100, "x", TransactionOperation.SELL);
        validateTransaction(find(transactionsList, "A"), "A", 777, "y", TransactionOperation.BUY);
        validateTransaction(find(transactionsList, "A"), "A", 210, "z", TransactionOperation.BUY);

    }

    private void validateTransaction(Transaction transaction, String ticket, int quantity, String account, Enum operation) {
        assertEquals(ticket, transaction.getTicket());
        assertEquals(quantity, transaction.getQuantity());
        assertEquals(account, transaction.getAccount());
        assertEquals(operation, transaction.getOperation());
    }

    private Transaction find(List<Transaction> transactionList, String ticket) {
        for (Transaction transaction : transactionList) {
            if (transaction.getTicket().equals(ticket)) {
                return transaction;
            }
        }
        return null;
    }

}
