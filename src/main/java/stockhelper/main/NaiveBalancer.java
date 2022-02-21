package stockhelper.main;

import lombok.AllArgsConstructor;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class NaiveBalancer implements PortfolioBalancer {
    private Market market;


    @Override
    public Map<String, Integer> balance(List<InvestmentLine> currentItems, Map<String, Double> allocations) {
        InvestmentLine singleStock = currentItems.get(0);
        Currency stockValue = market.getStockValue(singleStock.getTicket());

        Double totalValue = stockValue.getAmount() * singleStock.getQuantity();


        Map.Entry<String, Double> first = allocations.entrySet().stream().findFirst().get();
        Currency stockCurrency = market.getStockValue(first.getKey());
        Double currencyValue = stockCurrency.getAmount();

        int v = (int) ((totalValue * first.getValue()) / currencyValue);


        Map<String, Integer> results = new HashMap<>();
        results.put(first.getKey(), v);
        return results;
    }
}
