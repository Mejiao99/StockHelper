package stockhelper.main;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class InvestmentLine {
    private String ticket;
    private Integer quantity;
    private String account;
}
