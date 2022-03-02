package stockhelper.main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AssetsBracketsCalculatorImplTest {
    private AssetsBracketsCalculatorImpl calculator;

    @BeforeEach
    public void setup() {
        calculator = new AssetsBracketsCalculatorImpl();
    }

    @Test
    public void when_execute_current_transaction_assets_convert_to_map_date_portfolio() {
        // Preparation
        List<Transaction> transactions = Arrays.asList(
                // add Instant.parse("2007-05-02T18:00:00.00Z") in constructor
                new Transaction("a", 487, "x", TransactionOperation.BUY, Instant.parse("2007-05-02T18:00:00.00Z")),
                // add Instant.parse("2008-05-02T18:00:00.00Z") in constructor
                new Transaction("b", 122, "x", TransactionOperation.BUY, Instant.parse("2008-05-02T18:00:00.00Z")));

        // Execute
        Map<LocalDate, List<InvestmentLine>> assets = calculator.calculate(transactions);

        // Validations
        assertNotNull(assets);
        assertEquals(2, assets.size());

        assertEquals(assets.get(LocalDate.of(2007, 05, 02)), new InvestmentLine("a", 487, "x"));
        assertEquals(assets.get(LocalDate.of(2008, 05, 02)), new InvestmentLine("b", 122, "x"));
    }

}
