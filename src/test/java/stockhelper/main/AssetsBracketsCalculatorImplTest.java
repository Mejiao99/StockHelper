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
                new Transaction("a", 487, "x", TransactionOperation.BUY, Instant.parse("2007-05-02T18:00:00.00Z")),
                new Transaction("a", 122, "x", TransactionOperation.BUY, Instant.parse("2008-05-02T18:00:00.00Z")));


        // Execute
        Map<LocalDate, List<InvestmentLine>> assets = calculator.calculate(transactions);
        List<InvestmentLine> startList = assets.get(LocalDate.of(2007, 05, 02));
        List<InvestmentLine> endList = assets.get(LocalDate.of(2008, 05, 02));

        // Validations
        assertNotNull(assets);
        assertEquals(2, assets.size());

        assertEquals(assets.get(LocalDate.of(2007, 05, 02)), startList);
        assertEquals(assets.get(LocalDate.of(2008, 05, 02)), endList);

    }

}
