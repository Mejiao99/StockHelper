package stockhelper.main;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class PortfolioValueCalculatorImpl implements PortfolioValueCalculator {
    private Market market;

    @Override
    public double calculate(final List<InvestmentLine> lines, final String currency) {
        double totalValue = 0;

        for (final InvestmentLine line : lines) {
            Currency stockValue = market.getStockValue(line.getTicket());
            double conversionRate = market.exchangeRate(stockValue.getSymbol(), currency);
            double eachValue = stockValue.getAmount() * line.getQuantity();
            double currencyValue = eachValue * conversionRate;
            totalValue = totalValue + currencyValue;
        }

        return totalValue;
    }
}
