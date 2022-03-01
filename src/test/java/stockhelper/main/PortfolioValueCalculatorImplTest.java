package stockhelper.main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class PortfolioValueCalculatorImplTest {
    private Market market;
    private PortfolioValueCalculatorImpl valueCalculator;

    @BeforeEach
    public void setup() {
        market = mock(Market.class);
        valueCalculator = new PortfolioValueCalculatorImpl();

        when(market.getStockValue("A")).thenReturn(new Currency(10.0, "USD"));
        when(market.getStockValue("B")).thenReturn(new Currency(20.0, "USD"));

        when(market.exchangeRate("USD", "CAD")).thenReturn(1.265822784810127);
        when(market.exchangeRate("USD", "USD")).thenReturn(1.0);

        when(market.exchangeRate("CAD", "USD")).thenReturn(0.79);
        when(market.exchangeRate("CAD", "CAD")).thenReturn(1.0);
    }

    @Test
    public void portfolio_usd_value() {
        // Preparation
        // Ticket A = 10 USD, Ticket B = 20 USD
        List<InvestmentLine> investments = Arrays.asList(
                new InvestmentLine("A", 78, "x"),
                new InvestmentLine("B", 100, "x"));

        // Execution
        double value = valueCalculator.calculate(investments, "usd");

        // Validations
        assertEquals(2780,value);
    }

    @Test
    public void portfolio_cad_value() {
        // Preparation
        // Ticket A = 10 USD, Ticket B = 20 USD
        List<InvestmentLine> investments = Arrays.asList(
                new InvestmentLine("A", 78, "x"),
                new InvestmentLine("B", 100, "x"));

        // Execution
        double value = valueCalculator.calculate(investments, "cad");

        // Validations
        assertEquals(3518,value);
    }

}
