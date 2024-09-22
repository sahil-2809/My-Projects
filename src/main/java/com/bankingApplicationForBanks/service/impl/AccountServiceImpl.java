package com.bankingApplicationForBanks.service.impl;

import com.bankingApplicationForBanks.dto.AccountDto;
import com.bankingApplicationForBanks.entity.Account;
import com.bankingApplicationForBanks.mapper.AccountMapper;
import com.bankingApplicationForBanks.repository.AccountRepository;
import com.bankingApplicationForBanks.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        super();
        this.accountRepository = accountRepository;
    }// constructor injection

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);

        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account does nor exist"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {

        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account does nor exist"));

       double totalBalance = account.getBalance() + amount;
       account.setBalance(totalBalance);
       Account savedAccount = accountRepository.save(account);


        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {

        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account does nor exist"));

        if(account.getBalance() < amount) {
            throw new RuntimeException("Insufficient Balance");
        }

        double totalBalance = account.getBalance()-amount;
        account.setBalance(totalBalance);
        Account savedAccount = accountRepository.save(account);


        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {

        return accountRepository.findAll().stream().map((account) -> AccountMapper.mapToAccountDto(account)).collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {

        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account does nor exist"));

        accountRepository.delete(account);
    }
}
