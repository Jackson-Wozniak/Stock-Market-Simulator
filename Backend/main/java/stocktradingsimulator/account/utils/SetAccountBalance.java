package stocktradingsimulator.account.utils;

import stocktradingsimulator.account.exception.AccountBalanceException;
import stocktradingsimulator.account.model.entity.Account;

public class SetAccountBalance {

    public static void setAccountBalance(Account account, Double amountToAdd) throws AccountBalanceException {
        account.setAccountBalance(account.getAccountBalance() + amountToAdd);
        if(account.getAccountBalance() < 0 ){
            throw new AccountBalanceException("Must have more money in account");
        }
    }
}
