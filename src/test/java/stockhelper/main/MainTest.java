package stockhelper.main;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainTest {
    @Test
    public static void main(String[] args) {
        List<InvestmentLine> fromAllocations = Arrays.asList(new InvestmentLine("A", 999, "c"));
        List<InvestmentLine> toAllocations = Arrays.asList(new InvestmentLine("A", 750, "c"));
        Set<InvestmentLine> combinedInvestment = new HashSet<>();
        combinedInvestment.addAll(fromAllocations);
        combinedInvestment.addAll(toAllocations);
        System.out.println(combinedInvestment);
    }
}

