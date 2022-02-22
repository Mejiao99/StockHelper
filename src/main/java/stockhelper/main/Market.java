package stockhelper.main;


public interface Market {

    Currency getStockValue(String ticket);

    //TODO: ASSUME ONE TYPE
    // ConversionRate(USD, COP) = 4000
    // ConversionRate(COP, USD) = 0.00025

    default double exchangeRate(String from, String to) {
        return 1.0;
    }

}
