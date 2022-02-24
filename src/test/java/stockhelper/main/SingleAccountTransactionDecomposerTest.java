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
        List<InvestmentLine> fromAllocations = Arrays.asList(new InvestmentLine("A", 100, "c1"));
        List<InvestmentLine> toAllocations = Arrays.asList(new InvestmentLine("A", 75, "c1"));

        // Execution
        List<Transaction> transactionsList = decomposer.decompose(fromAllocations, toAllocations);

        // Validations
        assertNotNull(transactionsList);
        assertEquals(1, transactionsList.size());
        assertEquals("A", transactionsList.get(0).getTicket());
        assertEquals(25, transactionsList.get(0).getQuantity());
        assertEquals("c1", transactionsList.get(0).getAccount());
        assertEquals(TransactionOperation.SELL, transactionsList.get(0).getOperation());
    }
}
