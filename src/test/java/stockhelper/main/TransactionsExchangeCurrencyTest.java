package stockhelper.main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransactionsExchangeCurrencyTest {

    private Market market;
    private TransactionsExchangeCurrency exchange;

    @BeforeEach
    public void setup() {
        exchange = new TransactionsExchangeCurrency();

        market = mock(Market.class);
        when(market.getStockValue("A")).thenReturn(new Currency(10.0, "USD"));
        when(market.getStockValue("B")).thenReturn(new Currency(20.0, "USD"));

        when(market.exchangeRate("USD", "CAD")).thenReturn(1.265822784810127);
        when(market.exchangeRate("USD", "USD")).thenReturn(1.0);

        when(market.exchangeRate("CAD", "USD")).thenReturn(0.79);
        when(market.exchangeRate("CAD", "CAD")).thenReturn(1.0);
    }

    @Test
    public void buy_usd_to_cad_transaction() {
        // Preparation
        List<Transaction> transactionsFrom = Arrays.asList(
                new Transaction("A", 145, "x", TransactionOperation.BUY),
                new Transaction("B", 167, "x", TransactionOperation.BUY));
        // Ticket A = 10 USD, Ticket B = 20 USD
        // Ticket A = 12 CAD, Ticket B = 25 CAD
        // Ticket A = 1450 USD, Ticket B = 3340 USD
        // Ticket A = 1835 CAD, Ticket B = 4227 CAD

        // Execution
        List<Transaction> transactions = exchange.currencyExchange(transactionsFrom, "cad");

        // Validations
        // Validations
        assertNotNull(transactions);
        assertEquals(2, transactions.size());

        assertEquals(152, (transactions).get(0).getQuantity());
        assertEquals(169, (transactions).get(1).getQuantity());
    }

}
