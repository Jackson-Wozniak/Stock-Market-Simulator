package stocktradingsimulator.account.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stocktradingsimulator.account.exception.AccountBalanceException;
import stocktradingsimulator.account.exception.AccountNotFoundException;
import stocktradingsimulator.account.exception.InvalidAccountException;
import stocktradingsimulator.account.model.entity.Account;
import stocktradingsimulator.account.model.payload.AccountTransaction;
import stocktradingsimulator.account.repository.AccountRepository;
import stocktradingsimulator.account.utils.SetAccountBalance;

@Service
@AllArgsConstructor
public class AccountService {

    @Autowired
    private final AccountRepository accountRepository;

    public Account getAccountByName(String username) throws AccountNotFoundException {
        return accountRepository.findById(username)
                .orElseThrow(() -> new AccountNotFoundException("No account with that username"));
    }

    public void saveAccount(Account account){
        accountRepository.save(account);
    }

    public void createNewAccount(String username) throws InvalidAccountException {
        if(doesAccountExist(username)){
            throw new InvalidAccountException("Account already exists with that username");
        }
        accountRepository.save(new Account(username));
    }

    public boolean doesAccountExist(String username){
        try{
            getAccountByName(username);
            return true;
        }catch(AccountNotFoundException ex){
            return false;
        }
    }

    public void updateAccountBalance(AccountTransaction accountTransaction) throws AccountNotFoundException, AccountBalanceException {
        Account account = getAccountByName(accountTransaction.getUsername());
        SetAccountBalance.setAccountBalance(account, accountTransaction.getAmountToAdd());
        saveAccount(account);
    }
}
