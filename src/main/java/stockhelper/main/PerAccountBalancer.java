package stockhelper.main;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;

@AllArgsConstructor
public class PerAccountBalancer implements PortfolioBalancer {
    private Market market;

    @Override
    public List<InvestmentLine> balance(List<InvestmentLine> currentItems, Map<String, Double> allocations) {

        for (InvestmentLine stock : currentItems) {
            String ticket = stock.getTicket();
            String account = stock.getAccount();

            double totalValue = 0;
            if (stock.getTicket().equals(ticket) && stock.getAccount().equals(account)) {
                Currency stockValue = market.getStockValue(stock.getTicket());
                double conversionRate = market.exchangeRate(stockValue.getSymbol(), "USD");

                double eachValue = stockValue.getAmount() * stock.getQuantity();
                double eachValueUSD = eachValue * conversionRate;
                totalValue = totalValue + eachValueUSD;
                System.out.println(totalValue);
            }
        }

        return null;
    }
}
