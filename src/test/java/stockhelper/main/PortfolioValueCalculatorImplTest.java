package stockhelper.main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PortfolioValueCalculatorImplTest {

    private PortfolioValueCalculatorImpl valueCalculator;

    @BeforeEach
    public void setup() {
        valueCalculator = new PortfolioValueCalculatorImpl();
    }

    @Test
    public void buy_usd_to_cad_transaction() {
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

}
