package org.api.stocktradingservice.account.controller;

import lombok.AllArgsConstructor;
import org.api.stocktradingservice.account.client.StockMarketRestClient;
import org.api.stocktradingservice.account.exception.InvalidAccountException;
import org.api.stocktradingservice.account.service.AccountHistoryService;
import org.api.stocktradingservice.account.service.AccountService;
import org.api.stocktradingservice.account.exception.AccountBalanceException;
import org.api.stocktradingservice.account.exception.AccountNotFoundException;
import org.api.stocktradingservice.account.model.entity.Account;
import org.api.stocktradingservice.account.model.entity.AccountHistory;
import org.api.stocktradingservice.account.model.payload.AccountTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/account")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountHistoryService accountHistoryService;

    @RequestMapping(value = "/{username}")
    public Account getAccountByUsername(@PathVariable String username) throws AccountNotFoundException {
        return accountService.getAccountByName(username);
    }

    @PostMapping(value = "/{username}/{password}")
    public void createAccount(@PathVariable String username, @PathVariable String password) throws InvalidAccountException {
        if(username.equalsIgnoreCase("trading-bot")){
            throw new InvalidAccountException("Cannot Create Account With Invalid Username");
        }
        accountService.createNewAccount(username, password);
    }

    @PostMapping(value = "/deposit")
    public void depositToAccount(@RequestBody AccountTransaction accountTransaction) throws AccountNotFoundException, AccountBalanceException {
        accountService.updateBalanceAndSave(accountTransaction);
    }

    @GetMapping(value = "/history/{username}")
    public List<AccountHistory> getAccountHistory(@PathVariable String username) throws AccountNotFoundException, AccountBalanceException {
        return accountHistoryService.findHistoryOfAccount(username);
    }
}
