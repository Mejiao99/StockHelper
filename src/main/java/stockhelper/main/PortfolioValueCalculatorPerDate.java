package stockhelper.main;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface PortfolioValueCalculatorPerDate {
    Map<Currency, LocalDate> calculate(final List<InvestmentLine> balancedInvestments, final List<Transaction> transactions,
                                       final LocalDate startDate, final LocalDate endDate);
}
