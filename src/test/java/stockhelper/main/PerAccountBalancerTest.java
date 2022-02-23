package stockhelper.main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PerAccountBalancerTest {
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
    public void one_to_one_per_account() {
        // Preparation
        InvestmentLine c1StockA = new InvestmentLine("A", 100, "c1");

        InvestmentLine c2StockA = new InvestmentLine("A", 20, "c2");

        Map<String, Double> allocations = new HashMap<>();
        allocations.put("B", 1.0);

        // Execution
        List<InvestmentLine> newAllocations = balancer.balance(Arrays.asList(c1StockA, c2StockA), allocations);

        // Validations:
        assertNotNull(newAllocations);
        assertEquals(2, newAllocations.size());
        validateInvestmentLine(find(newAllocations, "B"), "B", 50, "c1");
        validateInvestmentLine(find(newAllocations, "B"), "B", 10, "c2");
    }
}
