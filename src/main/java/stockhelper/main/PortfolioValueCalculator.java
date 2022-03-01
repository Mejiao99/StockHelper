package stockhelper.main;

import java.util.List;

public interface PortfolioValueCalculator {
    int valueCalculate(List<InvestmentLine> currentItems, String currency);
}

