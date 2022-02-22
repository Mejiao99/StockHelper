package stockhelper.main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class NaiveBalancerTest {
    private Market market;
    private NaiveBalancer balancer;

    @BeforeEach
    public void setup() {
        market = mock(Market.class);
        balancer = new NaiveBalancer(market);
        when(market.getStockValue("A")).thenReturn(new Currency(10.0, "USD"));
        when(market.getStockValue("B")).thenReturn(new Currency(20.0, "USD"));
        when(market.getStockValue("C")).thenReturn(new Currency(5.0, "USD"));
        when(market.getStockValue("D")).thenReturn(new Currency(15.0, "USD"));

        when(market.getStockValue("X")).thenReturn(new Currency(7.0, "CAD"));
        when(market.getStockValue("Y")).thenReturn(new Currency(9.0, "CAD"));
        when(market.getStockValue("Z")).thenReturn(new Currency(12.0, "CAD"));

        when(market.exchangeRate("USD", "CAD")).thenReturn(1.265822784810127);
        when(market.exchangeRate("USD", "USD")).thenReturn(1.0);

        when(market.exchangeRate("CAD", "USD")).thenReturn(0.79);
        when(market.exchangeRate("CAD", "CAD")).thenReturn(1.0);

    }

    @Test
    public void simple_single_stock() {
        // Preparation
        // A = $10, B = $20

        InvestmentLine singleStock = new InvestmentLine("A", 100, "c1");


        // Execution
        Map<String, Integer> newAllocations = balancer.balance(Arrays.asList(singleStock), Collections.singletonMap("B", 1.0));

        // Validations:
        // newAllocations have two elements
        // "A" -> 0
        // "B" -> 50
        assertNotNull(newAllocations);
        assertEquals(1, newAllocations.size());
        assertEquals(50, newAllocations.get("B"));

    }

    @Test
    public void simple_tree_to_one_stock() {
        // Preparation
        // A = $10, B = $20

        InvestmentLine stockA = new InvestmentLine("A", 100, "c1");
        InvestmentLine stockB = new InvestmentLine("B", 90, "c1");
        InvestmentLine stockC = new InvestmentLine("C", 125, "c1");

        // Execution
        Map<String, Integer> newAllocations = balancer.balance(Arrays.asList(stockA, stockB, stockC), Collections.singletonMap("D", 1.0));

        // Validations:
        assertNotNull(newAllocations);
        assertEquals(1, newAllocations.size());
        assertEquals(228, newAllocations.get("D"));
        // 10 * 100 + 20*90 + 5*125 = 3425 = TOT MONEY
        // 3425 / 15.0 = 228.333333333

    }

    @Test
    public void one_to_three_stock() {
        // Preparation
        InvestmentLine stockA = new InvestmentLine("A", 100, "c1");

        NaiveBalancer balancer = new NaiveBalancer(market);
        Map<String, Double> allocations = new HashMap<>();
        allocations.put("B", 0.25);
        allocations.put("C", 0.35);
        allocations.put("D", 0.40);

        // Execution

        Map<String, Integer> newAllocations = balancer.balance(Arrays.asList(stockA), allocations);

        // Validations:
        assertNotNull(newAllocations);
        assertEquals(3, newAllocations.size());
        assertEquals(12, newAllocations.get("B"));
        assertEquals(70, newAllocations.get("C"));
        assertEquals(26, newAllocations.get("D"));
        // 10 * 100 + 20*90 + 5*125 = 3425 = TOT MONEY
        // 3425 / 15.0 = 228.333333333

    }

    @Test
    public void three_to_three_stock() {
        // Preparation
        InvestmentLine stockA = new InvestmentLine("A", 100, "c1");
        InvestmentLine stockB = new InvestmentLine("B", 90, "c1");
        InvestmentLine stockC = new InvestmentLine("C", 125, "c1");
        Map<String, Double> allocations = new HashMap<>();
        allocations.put("B", 0.25);
        allocations.put("C", 0.35);
        allocations.put("D", 0.40);

        // Execution
        Map<String, Integer> newAllocations = balancer.balance(Arrays.asList(stockA, stockB, stockC), allocations);

        // Validations:
        assertNotNull(newAllocations);
        assertEquals(3, newAllocations.size());
        assertEquals(42, newAllocations.get("B"));
        assertEquals(239, newAllocations.get("C"));
        assertEquals(91, newAllocations.get("D"));
        // 10 * 100 + 20*90 + 5*125 = 3425 = TOT MONEY
        // 3425 / 15.0 = 228.333333333

    }

    @Test
    public void usd_to_cad_stock() {
        // Preparation
        InvestmentLine stockA = new InvestmentLine("A", 100, "c1");
        Map<String, Double> allocations = new HashMap<>();
        allocations.put("X", 1.0);

        // Execution
        Map<String, Integer> newAllocations = balancer.balance(Arrays.asList(stockA), allocations);

        // Validations:
        assertNotNull(newAllocations);
        assertEquals(1, newAllocations.size());
        assertEquals(180, newAllocations.get("X"));

    }

    @Test
    public void cad_to_usd_stock() {
        // Preparation
        InvestmentLine stockA = new InvestmentLine("X", 100, "c1");
        Map<String, Double> allocations = new HashMap<>();
        allocations.put("A", 1.0);

        // Execution
        Map<String, Integer> newAllocations = balancer.balance(Arrays.asList(stockA), allocations);

        // Validations:
        assertNotNull(newAllocations);
        assertEquals(1, newAllocations.size());
        assertEquals(55, newAllocations.get("A"));

    }

    @Test
    public void empty_stock() {
        // Preparation
        Map<String, Double> allocations = new HashMap<>();
        allocations.put("A", 1.0);

        // Execution
        Map<String, Integer> newAllocations = balancer.balance(Arrays.asList(), allocations);

        // Validations:
        assertNotNull(newAllocations);
        assertEquals(0, newAllocations.size());

    }


    // TODO: one test per empty list, empty map, null list, null map


}