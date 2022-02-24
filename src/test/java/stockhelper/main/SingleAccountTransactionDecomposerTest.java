package stockhelper.main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SingleAccountTransactionDecomposerTest {
    private SingleAccountTransactionDecomposer transactionDecomposer;

    @BeforeEach
    public void setup() {

    }

    @Test
    public void single_transaction_decomposer() {
        // Preparation
        InvestmentLine StockAFrom = new InvestmentLine("A", 100, "c1");
        InvestmentLine StockATo = new InvestmentLine("A", 75, "c1");

        List<InvestmentLine> fromAllocations = new ArrayList<>();
        fromAllocations.add(StockAFrom);
        List<InvestmentLine> toAllocations = new ArrayList<>();
        toAllocations.add(StockATo);

        // Execution
        List<Transaction> transactionsList = transactionDecomposer.decompose(fromAllocations, toAllocations);

        // Validations
        assertNotNull(fromAllocations);
        assertNotNull(toAllocations);
        assertEquals(1, fromAllocations.size());
        assertEquals(1, toAllocations.size());
        assertEquals("SELL", transactionsList.get(0).getOperation());
        assertEquals("A", transactionsList.get(0).getTicket());
        assertEquals("25", transactionsList.get(0).getQuantity());
    }

}
