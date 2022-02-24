package stockhelper.main;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;


@AllArgsConstructor
@Getter
@ToString
public class InvestmentLine {
    private String ticket;
    private Integer quantity;
    private String account;
}
