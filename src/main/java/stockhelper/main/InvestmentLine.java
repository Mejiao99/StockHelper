package stockhelper.main;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class InvestmentLine {
    private String Ticket;
    private Currency Value;
    private Integer Quantity;
    private String Account;
}
