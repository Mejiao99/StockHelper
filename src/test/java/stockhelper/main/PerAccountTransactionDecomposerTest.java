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
    public void buy_few_stocks() {
        // Preparation
        List<InvestmentLine> fromAllocations = Arrays.asList(
                new InvestmentLine("A", 512, "x"),
                new InvestmentLine("B", 100, "x")
        );
        List<InvestmentLine> toAllocations = Arrays.asList(
                new InvestmentLine("A", 1024, "x"),
                new InvestmentLine("A", 950, "y"),
                new InvestmentLine("A", 210, "z")
        );

        // Execution
        List<Transaction> transactionsList = decomposer.decompose(fromAllocations, toAllocations);

        // Validations
        assertNotNull(transactionsList);
        assertEquals(4, transactionsList.size());

        validateTransaction(find(transactionsList, "A"), "A", 512, "x", TransactionOperation.BUY);
        validateTransaction(find(transactionsList, "B"), "B", 100, "x", TransactionOperation.SELL);
        validateTransaction(find(transactionsList, "A"), "A", 950, "y", TransactionOperation.BUY);
        validateTransaction(find(transactionsList, "B"), "A", 210, "z", TransactionOperation.BUY);
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
