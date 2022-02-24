package stockhelper.main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SingleAccountTransactionDecomposerTest {
    private SingleAccountTransactionDecomposer decomposer;

    @BeforeEach
    public void setup() {
        decomposer = new SingleAccountTransactionDecomposer();
    }

    @Test
    public void single_transaction_decomposer() {
        // Preparation
        List<InvestmentLine> fromAllocations = Arrays.asList(new InvestmentLine("A", 999, "c1"));
        List<InvestmentLine> toAllocations = Arrays.asList(new InvestmentLine("A", 750, "c1"));

        // Execution
        List<Transaction> transactionsList = decomposer.decompose(fromAllocations, toAllocations);
        Transaction transaction = transactionsList.get(0);

        // Validations
        assertNotNull(transactionsList);
        assertEquals(1, transactionsList.size());
        assertEquals("A", transaction.getTicket());
        assertEquals(249, transaction.getQuantity());
        assertEquals("c1", transaction.getAccount());
        assertEquals(TransactionOperation.SELL, transaction.getOperation());
    }
}
