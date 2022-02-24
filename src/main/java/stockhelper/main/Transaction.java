package stockhelper.main;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Transaction {
    private String ticket;
    private Integer quantity;
    private String account;
    private TransactionOperation operation;
}
