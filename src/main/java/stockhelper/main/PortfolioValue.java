package stockhelper.main;

import java.util.List;

public interface PortfolioValue {
    List<InvestmentLine> valueCalculator(List<InvestmentLine> currentItems, String currency);
}

