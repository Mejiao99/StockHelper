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
    private PerAccountBalancer balancer;

    @BeforeEach
    public void setup() {
        market = mock(Market.class);
        balancer = new PerAccountBalancer(new SingleAccountBalancer(market));
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
        System.out.println(newAllocations);
        validateInvestmentLine(find(newAllocations, "B", "c1"), "B", 50, "c1");

        validateInvestmentLine(find(newAllocations, "B", "c2"), "B", 10, "c2");
    }

    private void validateInvestmentLine(InvestmentLine investmentLine, String ticket, int quantity, String account) {
        assertEquals(ticket, investmentLine.getTicket());
        assertEquals(quantity, investmentLine.getQuantity());
        assertEquals(account, investmentLine.getAccount());
    }

    private InvestmentLine find(List<InvestmentLine> newAllocations, String ticket, String account) {


        for (InvestmentLine stock : newAllocations) {
            if (stock.getTicket().equals(ticket) && stock.getAccount().equals(account)) {
                return stock;
            }
        }
        return null;
    }
    // TODO: add validations more stocks, more accounts
    // ¿Qué transacciones tengo que hacer para llegar a lo esperado?
    // Dos tipos de transacción vender o comprar ¿Qué cuénta, qué ticket, cuánto? ¿Vender o comprar?
    // Entradas: 1. Lista de inversiones 2. Lista de lo que quiero
    // Salida: Lista de transacciones (nueva clase)
    // Cuenta 1 -> accion A -> Vender 10
    // Ejemplo:
    // Inversiones actuales = A:10:C1, B:20:C1
    // Lista de lo que quiero = A:20:C1, C:10:C2
    // Salida: A:10:C1:COMPRAR, B:20:C1:VENDER, C:10:C2:COMPRAR

}
