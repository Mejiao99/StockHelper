package stockhelper.main;

import java.util.List;

public interface PortfolioBalancer {

    List<InvestmentLine> balancer(List<InvestmentLine> currentList, List<InvestmentLine> wishList);

}
