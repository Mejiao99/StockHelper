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
    public void only_buy_single_stock() {
        // Preparation
        List<InvestmentLine> fromAllocations = Arrays.asList();
        List<InvestmentLine> toAllocations = Arrays.asList(new InvestmentLine("A", 750, "c"));


        // Execution
        List<Transaction> transactionsList = decomposer.decompose(fromAllocations, toAllocations);

        // Validations
        assertNotNull(transactionsList);
        assertEquals(1, transactionsList.size());
        Transaction transaction = transactionsList.get(0);
        assertEquals("A", transaction.getTicket());
        assertEquals(750, transaction.getQuantity());
        assertEquals("c", transaction.getAccount());
        assertEquals(TransactionOperation.BUY, transaction.getOperation());
    }

    @Test
    public void single_stock_sell() {
        // Preparation
        List<InvestmentLine> fromAllocations = Arrays.asList(new InvestmentLine("A", 999, "c"));
        List<InvestmentLine> toAllocations = Arrays.asList(new InvestmentLine("A", 750, "c"));

        // Execution
        List<Transaction> transactionsList = decomposer.decompose(fromAllocations, toAllocations);

        // Validations
        assertNotNull(transactionsList);
        assertEquals(1, transactionsList.size());
        Transaction transaction = transactionsList.get(0);
        assertEquals("A", transaction.getTicket());
        assertEquals(249, transaction.getQuantity());
        assertEquals("c", transaction.getAccount());
        assertEquals(TransactionOperation.SELL, transaction.getOperation());
    }
}
