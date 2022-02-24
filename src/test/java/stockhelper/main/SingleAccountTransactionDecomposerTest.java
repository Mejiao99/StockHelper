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

    }

    @Test
    public void single_transaction_decomposer() {
        // Preparation
        InvestmentLine stockAFrom = new InvestmentLine("A", 100, "c1");
        InvestmentLine stockATo = new InvestmentLine("A", 75, "c1");

        List<InvestmentLine> fromAllocations = Arrays.asList(stockAFrom);
        List<InvestmentLine> toAllocations = Arrays.asList(stockATo);

        // Execution
        List<Transaction> transactionsList = decomposer.decompose(fromAllocations, toAllocations);

        // Validations
        assertNotNull(transactionsList);
        assertEquals(1, transactionsList.size());
        assertEquals(TransactionOperation.SELL, transactionsList.get(0).getOperation());
        assertEquals("A", transactionsList.get(0).getTicket());
        assertEquals(25, transactionsList.get(0).getQuantity());
    }

}
