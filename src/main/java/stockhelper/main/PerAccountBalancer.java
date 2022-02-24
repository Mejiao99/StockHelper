package stockhelper.main;

import lombok.AllArgsConstructor;

import java.util.*;

@AllArgsConstructor
public class PerAccountBalancer implements PortfolioBalancer {
    private SingleAccountBalancer singleAccountBalancer;

    @Override
    public List<InvestmentLine> balance(List<InvestmentLine> currentItems, Map<String, Double> allocations) {
        List<String> accounts = getAllAccounts(currentItems);

        List<InvestmentLine> result = new ArrayList<>();
        for (String account : accounts) {
            // Buscar todas las acciones de esa cuenta
            List<InvestmentLine> accountLines = filterAccount(currentItems, account);
            // balancear cuenta accountLines
            List<InvestmentLine> balancedLines = singleAccountBalancer.balance(accountLines, allocations);
            // cuentas balanceadas se añaden al resultado
            result.addAll(balancedLines);
        }

        return result;
    }

    private List<String> getAllAccounts(List<InvestmentLine> currentItems) {
        Set<String> accounts = new HashSet<>();
        for (InvestmentLine line : currentItems) {
            accounts.add(line.getAccount());
        }
        return new ArrayList<>(accounts);
    }

    private List<InvestmentLine> filterAccount(List<InvestmentLine> currentItems, String account) {
        List<InvestmentLine> accountList = new ArrayList<>();
        for (InvestmentLine line : currentItems) {
            if (line.getAccount().equals(account)) {
                accountList.add(line);
            }
        }
        return accountList;
    }
}
