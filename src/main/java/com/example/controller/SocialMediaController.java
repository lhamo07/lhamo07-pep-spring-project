package com.example.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
  @Autowired
    AccountService accountService;
    @Autowired
    MessageService MessageService;
    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account){
      Optional<Account>registered=accountService.register(account);
      if(!registered.isPresent())  {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

      }else if(registered.get().getAccountId() == null){
        return ResponseEntity.status(HttpStatus.CONFLICT).build(); 

      }

      return ResponseEntity.ok(registered.get());

      
    }
    @PostMapping("/login")
  public ResponseEntity<Account> login(@RequestBody Account account) {
    Optional<Account> loggedIn = accountService.login(account);

    if (!loggedIn.isPresent()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    return ResponseEntity.ok(loggedIn.get());
  }
   @PostMapping("/messages")
  public ResponseEntity<Message> createMessage(@RequestBody Message message) {
    Optional<Message> createdMessage = MessageService.createdMessage(message);

    if (!createdMessage.isPresent()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    return ResponseEntity.ok(createdMessage.get());
  }


}
