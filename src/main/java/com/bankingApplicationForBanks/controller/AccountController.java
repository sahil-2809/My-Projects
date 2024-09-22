package com.bankingApplicationForBanks.controller;
import com.bankingApplicationForBanks.dto.AccountDto;
import com.bankingApplicationForBanks.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private AccountService accountService;

    public AccountController(AccountService accountService) {
        super();
        this.accountService = accountService;
    }

    //Add account Rest API
    @PostMapping
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto) {

        System.out.println(accountDto);

        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);

    }

     //get single account details

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id) {

        AccountDto accountDto = accountService.getAccountById(id);

        return ResponseEntity.ok(accountDto);
    }


    //deposit
    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountDto> deposit(@PathVariable Long id, @RequestBody Map<String, Double> request) {
        Double amount = request.get("amount");

        if (amount == null) {
            return ResponseEntity.badRequest().body(null); // Return a bad request response if amount is null
        }

        AccountDto accountDto = accountService.deposit(id, amount);
        return ResponseEntity.ok(accountDto);
    }

    //withdraw
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto> withdraw(@PathVariable Long id,@RequestBody Map<String, Double> request) {

        Double amount = request.get("amount");
        AccountDto accountDto = accountService.withdraw(id, amount);

        return ResponseEntity.ok(accountDto);
    }


    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts() {

        List<AccountDto> accountDto = accountService.getAllAccounts();

        return  ResponseEntity.ok(accountDto);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);

        return ResponseEntity.ok("Account deleted successfully !!");
    }
}
