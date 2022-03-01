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
                new InvestmentLine("A", 512, "c"),
                new InvestmentLine("B", 100, "c"),
                new InvestmentLine("A", 550, "c"),
                new InvestmentLine("B", 100, "c")
        );
        List<InvestmentLine> toAllocations = Arrays.asList(
                new InvestmentLine("A", 1024, "c"),
                new InvestmentLine("B", 230, "c"),
                new InvestmentLine("A", 950, "b"),
                new InvestmentLine("B", 210, "b")
        );

        // Execution
        List<Transaction> transactionsList = decomposer.decompose(fromAllocations, toAllocations);

        // Validations
        assertNotNull(transactionsList);
        assertEquals(4, transactionsList.size());

        validateTransaction(find(transactionsList, "A"), "A", 512, "c", TransactionOperation.BUY);
        validateTransaction(find(transactionsList, "B"), "B", 130, "c", TransactionOperation.BUY);
        validateTransaction(find(transactionsList, "A"), "C", 400, "b", TransactionOperation.BUY);
        validateTransaction(find(transactionsList, "B"), "C", 110, "b", TransactionOperation.BUY);
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
