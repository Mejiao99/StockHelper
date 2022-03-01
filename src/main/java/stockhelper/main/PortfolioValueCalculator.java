package stockhelper.main;

import java.util.List;

public interface PortfolioValueCalculator {
    double calculate(List<InvestmentLine> lines, String currency);
}

