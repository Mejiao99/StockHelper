package stockhelper.main;

import lombok.AllArgsConstructor;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;

@AllArgsConstructor
public class NaiveBalancer implements PortfolioBalancer {
    private Market market;

    @Override
    public List<InvestmentLine> balance(List<InvestmentLine> currentItems, Map<String, Double> allocations) {

        if (currentItems == null || currentItems.isEmpty()) {
            return emptyList();
        }

        if (allocations == null || allocations.isEmpty()) {
            return emptyList();
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

        List<InvestmentLine> balanceInvestmentList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : results.entrySet()) {
            InvestmentLine balanceStock = new InvestmentLine(entry.getKey(), entry.getValue(), "default");
            balanceInvestmentList.add(balanceStock);
        }
        return balanceInvestmentList;
    }
}
