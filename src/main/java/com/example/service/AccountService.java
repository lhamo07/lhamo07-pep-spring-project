package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
@Service
public class AccountService {
    AccountRepository accountRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository=accountRepository;
    }
     public Optional<Account> register(Account account) {
    if (account.getUsername().isEmpty()) {
      return Optional.empty();
    }
    if (account.getPassword().length() < 4) {
      return Optional.empty();
    }
    if (accountRepository.findByUsername(account.getUsername()).isPresent()) {
      return Optional.of(new Account());
    }
    return Optional.of(accountRepository.save(account));
  }
  public Optional<Account> login(Account account) {
    Optional<Account> foundAccount = accountRepository.findByUsername(account.getUsername());
    if (foundAccount.isPresent() && foundAccount.get().getPassword().equals(account.getPassword())) {
      return foundAccount;
    }
    return Optional.empty();
  }
}
