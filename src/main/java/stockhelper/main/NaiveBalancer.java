package stockhelper.main;

import lombok.AllArgsConstructor;


import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class NaiveBalancer implements PortfolioBalancer {
    private Market market;


    @Override
    public Map<String, Integer> balance(List<InvestmentLine> currentItems, Map<String, Double> allocations) {
        return null;
    }
}
