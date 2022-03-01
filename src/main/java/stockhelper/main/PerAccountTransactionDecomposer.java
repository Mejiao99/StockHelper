package stockhelper.main;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@ToString
public class PerAccountTransactionDecomposer {
    private SingleAccountTransactionDecomposer decomposer;

    public List<Transaction> decompose(List<InvestmentLine> from, List<InvestmentLine> to) {
        final Set<String> allAccounts = Stream.concat(from.stream(), to.stream())
                .map(InvestmentLine::getAccount)
                .collect(Collectors.toSet());

        List<Transaction> result = new ArrayList<>();
        for (String account : allAccounts) {
            List<InvestmentLine> fromAccountTransactions = filterAccount(from, account);
            List<InvestmentLine> toAccountTransactions = filterAccount(to, account);

            List<Transaction> transactions = decomposer.decompose(fromAccountTransactions, toAccountTransactions);
            result.addAll(transactions);
        }
        return result;
    }

    private List<InvestmentLine> filterAccount(List<InvestmentLine> investments, String account) {
        List<InvestmentLine> accountList = new ArrayList<>();
        for (InvestmentLine line : investments) {
            if (line.getAccount().equals(account)) {
                accountList.add(line);
            }
        }
        return accountList;
    }

    private Set<String> getAllAccounts(List<InvestmentLine> listA) {
        Set<String> accounts = new HashSet<>();
        for (InvestmentLine line : listA) {
            accounts.add(line.getAccount());
        }
        return accounts;
    }


}
