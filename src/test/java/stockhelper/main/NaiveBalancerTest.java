package stockhelper.main;

import org.junit.jupiter.api.Test;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class NaiveBalancerTest {


    @Test
    public void simple_single_stock() {
        // Preparation
        // A = $10, B = $20
        Market market = mock(Market.class);
        when(market.getStockValue("A")).thenReturn(new Currency(BigDecimal.valueOf(10), "USD"));
        when(market.getStockValue("B")).thenReturn(new Currency(BigDecimal.valueOf(20), "USD"));
        InvestmentLine singleStock = new InvestmentLine("A", 100, "c1");
        NaiveBalancer balancer = new NaiveBalancer(market);

        // Execution
        Map<String, Integer> newAllocations = balancer.balance(Arrays.asList(singleStock), Collections.singletonMap("B", 1.0));

        // Validations:
        // newAllocations have two elements
        // "A" -> 0
        // "B" -> 50
        assertNotNull(newAllocations);
        assertEquals(2, newAllocations.size());
        assertEquals(0, newAllocations.get("A"));
        assertEquals(50, newAllocations.get("B"));

    }

}