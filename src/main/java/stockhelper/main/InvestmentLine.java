package stockhelper.main;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;



@AllArgsConstructor
@Getter
@Builder(toBuilder=true)
public class InvestmentLine {
    private String ticket;
    private Integer quantity;
    private String account;
}
