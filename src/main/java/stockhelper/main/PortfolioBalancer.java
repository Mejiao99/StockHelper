package stockhelper.main;

import java.util.List;
import java.util.Map;

public interface PortfolioBalancer {

    Map<String, Integer> balance(List<InvestmentLine> currentItems, Map<String, Double> allocations);

}
