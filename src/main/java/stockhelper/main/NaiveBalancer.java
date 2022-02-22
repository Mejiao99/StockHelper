package stockhelper.main;

import lombok.AllArgsConstructor;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class NaiveBalancer implements PortfolioBalancer {
    private Market market;

    @Override
    public Map<String, Integer> balance(List<InvestmentLine> currentItems, Map<String, Double> allocations) {

        if (currentItems == null || currentItems.isEmpty()) {
            return Collections.emptyMap();
        }

        if (allocations == null || allocations.isEmpty()) {
            return Collections.emptyMap();
        }

        double totalValue = 0;

        for (InvestmentLine stock : currentItems) {

            Currency stockValue = market.getStockValue(stock.getTicket());
            double conversionRate = market.exchangeRate(stockValue.getSymbol(), "USD");

            double eachValue = stockValue.getAmount() * stock.getQuantity();
            double eachValueUSD = eachValue * conversionRate;

            totalValue = totalValue + eachValueUSD;
        }
        Map<String, Integer> results = new HashMap<>();
        for (Map.Entry<String, Double> entry : allocations.entrySet()) {

            String ticket = entry.getKey();
            double allocation = entry.getValue();

            Currency ticketCurrency = market.getStockValue(ticket);
            double conversionRate = market.exchangeRate(ticketCurrency.getSymbol(), "USD");

            double ticketAmount = ticketCurrency.getAmount();
            double priceUSD = ticketAmount * conversionRate;
            int stockQty = (int) ((totalValue * allocation) / priceUSD);

            results.put(ticket, stockQty);
        }
        return results;
    }
}
