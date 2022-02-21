package stockhelper.main;

import lombok.AllArgsConstructor;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class NaiveBalancer implements PortfolioBalancer {
    private Market market;


    @Override
    public Map<String, Integer> balance(List<InvestmentLine> currentItems, Map<String, Double> allocations) {
        double totalValue = 0;
        for (InvestmentLine stock : currentItems) {
            Currency stockValue = market.getStockValue(stock.getTicket());
            double eachValue = stockValue.getAmount() * stock.getQuantity();
            totalValue = totalValue + eachValue;

        }


        Map<String, Integer> results = new HashMap<>();

        for (Map.Entry<String, Double> entry : allocations.entrySet()) {
            String ticket = entry.getKey();
            double allocation = entry.getValue();

            Currency ticketCurrency = market.getStockValue(ticket);
            double ticketAmount = ticketCurrency.getAmount();
            int stockQty = (int) ((totalValue * allocation) / ticketAmount);
            results.put(ticket, stockQty);

        }


        return results;
    }
}
