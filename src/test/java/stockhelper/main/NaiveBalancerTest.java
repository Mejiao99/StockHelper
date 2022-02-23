package stockhelper.main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.*;

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
        InvestmentLine singleStock = new InvestmentLine("A", 100, "c1");

        // Execution
        List<InvestmentLine> newAllocations = balancer.balance(Arrays.asList(singleStock), Collections.singletonMap("B", 1.0));

        // Validations:
        assertNotNull(newAllocations);
        assertEquals(1, newAllocations.size());
        validateInvestmentLine(find(newAllocations, "B"), "B", 50, "default");
    }

    @Test
    public void simple_tree_to_one_stock() {
        // Preparation
        InvestmentLine stockA = new InvestmentLine("A", 100, "c1");
        InvestmentLine stockB = new InvestmentLine("B", 90, "c1");
        InvestmentLine stockC = new InvestmentLine("C", 125, "c1");

        // Execution
        List<InvestmentLine> newAllocations = balancer.balance(Arrays.asList(stockA, stockB, stockC), Collections.singletonMap("D", 1.0));

        // Validations:
        assertNotNull(newAllocations);
        assertEquals(1, newAllocations.size());
        validateInvestmentLine(find(newAllocations, "D"), "D", 228, "default");
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
        List<InvestmentLine> newAllocations = balancer.balance(Arrays.asList(stockA), allocations);

        // Validations:
        assertNotNull(newAllocations);
        assertEquals(3, newAllocations.size());
        validateInvestmentLine(find(newAllocations, "B"), "B", 12, "default");
        validateInvestmentLine(find(newAllocations, "C"), "C", 70, "default");
        validateInvestmentLine(find(newAllocations, "D"), "D", 26, "default");
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
        List<InvestmentLine> newAllocations = balancer.balance(Arrays.asList(stockA, stockB, stockC), allocations);

        // Validations:
        assertNotNull(newAllocations);
        assertEquals(3, newAllocations.size());
        validateInvestmentLine(find(newAllocations, "B"), "B", 42, "default");
        validateInvestmentLine(find(newAllocations, "C"), "C", 239, "default");
        validateInvestmentLine(find(newAllocations, "D"), "D", 91, "default");
    }

    @Test
    public void usd_to_cad_stock() {
        // Preparation
        InvestmentLine stockA = new InvestmentLine("A", 100, "c1");
        Map<String, Double> allocations = new HashMap<>();
        allocations.put("X", 1.0);

        // Execution
        List<InvestmentLine> newAllocations = balancer.balance(Arrays.asList(stockA), allocations);

        // Validations:
        assertNotNull(newAllocations);
        assertEquals(1, newAllocations.size());
        validateInvestmentLine(find(newAllocations, "X"), "X", 180, "default");
    }

    @Test
    public void cad_to_usd_stock() {
        // Preparation
        InvestmentLine stockA = new InvestmentLine("X", 100, "c1");
        Map<String, Double> allocations = new HashMap<>();
        allocations.put("A", 1.0);

        // Execution
        List<InvestmentLine> newAllocations = balancer.balance(Arrays.asList(stockA), allocations);

        // Validations:
        assertNotNull(newAllocations);
        assertEquals(1, newAllocations.size());
        validateInvestmentLine(find(newAllocations, "A"), "A", 55, "default");
    }

    @Test
    public void empty_stock() {
        // Preparation
        Map<String, Double> allocations = new HashMap<>();
        allocations.put("A", 1.0);

        // Execution
        List<InvestmentLine> newAllocations = balancer.balance(Arrays.asList(), allocations);

        // Validations:
        assertNotNull(newAllocations);
        assertEquals(0, newAllocations.size());
    }

    @Test
    public void empty_map() {
        // Preparation
        InvestmentLine stockA = new InvestmentLine("A", 100, "c1");
        InvestmentLine stockB = new InvestmentLine("B", 90, "c1");
        InvestmentLine stockC = new InvestmentLine("C", 125, "c1");
        Map<String, Double> allocations = new HashMap<>();

        // Execution
        List<InvestmentLine> newAllocations = balancer.balance(Arrays.asList(stockA, stockB, stockC), Collections.emptyMap());

        // Validations:
        assertNotNull(newAllocations);
        assertEquals(0, newAllocations.size());

    }

    @Test
    public void null_list() {
        // Preparation
        Map<String, Double> allocations = new HashMap<>();
        allocations.put("B", 0.25);
        allocations.put("C", 0.35);
        allocations.put("D", 0.40);

        // Execution
        List<InvestmentLine> newAllocations = balancer.balance(null, allocations);

        // Validations:
        assertNotNull(newAllocations);
        assertEquals(0, newAllocations.size());

    }

    @Test
    public void null_map() {
        // Preparation
        InvestmentLine stockA = new InvestmentLine("A", 100, "c1");
        InvestmentLine stockB = new InvestmentLine("B", 90, "c1");
        InvestmentLine stockC = new InvestmentLine("C", 125, "c1");

        // Execution
        List<InvestmentLine> newAllocations = balancer.balance(Arrays.asList(stockA, stockB, stockC), null);

        // Validations:
        assertNotNull(newAllocations);
        assertEquals(0, newAllocations.size());

    }
//
//    // TODO crear una nueva clase PerAccountBalancer por cada cuenta hace un balance esto retorna una lista de InvestmentLine y Allocation
//    // List(InvestLine) (Lista investment line, allocation)
//    //

    private void validateInvestmentLine(InvestmentLine investmentLine, String ticket, int quantity, String account) {
        assertEquals(ticket, investmentLine.getTicket());
        assertEquals(quantity, investmentLine.getQuantity());
        assertEquals(account, investmentLine.getAccount());
    }

    private InvestmentLine find(List<InvestmentLine> newAllocations, String ticket) {
        for (InvestmentLine stock : newAllocations) {
            if (stock.getTicket().equals(ticket)) {
                return stock;
            }
        }
        return null;
    }
}