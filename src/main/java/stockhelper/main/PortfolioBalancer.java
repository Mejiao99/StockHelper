package stockhelper.main;

import java.util.List;
import java.util.Map;

public interface PortfolioBalancer {
    // retornar listaInvestmentLine
    // elInvesmentLine debe tener una cuenta default
    List<InvestmentLine> balance(List<InvestmentLine> currentItems, Map<String, Double> allocations);

}
