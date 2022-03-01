package stockhelper.main;

import java.util.List;
import java.util.Map;

public interface PortfolioValue {
    List<InvestmentLine> valueCalculator(List<InvestmentLine> currentItems, String currency);
}

