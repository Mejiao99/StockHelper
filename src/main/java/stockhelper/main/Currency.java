package stockhelper.main;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class Currency {
    private Double Amount;
    private String Symbol;
}
