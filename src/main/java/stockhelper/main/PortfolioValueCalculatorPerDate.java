package stockhelper.main;

import java.util.Date;
import java.util.List;

public interface PortfolioValueCalculatorPerDate {
    int calculate(final List<Transaction> transactions, Date date);
}
