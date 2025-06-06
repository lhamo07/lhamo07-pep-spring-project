package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your
 * controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use
 * the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations.
 * You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */
@RestController
public class SocialMediaController {
  @Autowired
  AccountService accountService;
  @Autowired
  MessageService messageService;
  // 1. API should be able to process new User registrations.
  @PostMapping("/register")
  public ResponseEntity<Account> register(@RequestBody Account account) {
    Optional<Account> registered = accountService.register(account);
    if (!registered.isPresent()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    } else if (registered.get().getAccountId() == null) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();

    }

    return ResponseEntity.ok(registered.get());

  }
  // 2. API should be able to process User logins.
  @PostMapping("/login")
  public ResponseEntity<Account> login(@RequestBody Account account) {
    Optional<Account> loggedIn = accountService.login(account);

    if (!loggedIn.isPresent()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    return ResponseEntity.ok(loggedIn.get());
  }
  // 3. API should be able to process the creation of new messages.
  @PostMapping("/messages")
  public ResponseEntity<Message> createMessage(@RequestBody Message message) {
    Optional<Message> createdMessage = messageService.createdMessage(message);
    System.out.println("Created Message: " + createdMessage);
    if (!createdMessage.isPresent()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    return ResponseEntity.ok(createdMessage.get());
  }
  // 4.retrieve all messages.
  @GetMapping("/messages")
  public ResponseEntity<List<Message>> getMessages() {
    List<Message> messages = messageService.getMessages();
    return ResponseEntity.ok(messages);

  }
  // 5. retrieve a message by its ID.
  @GetMapping("messages/{messageId}")
  public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId) {
    Optional<Message> message = messageService.getMessageById(messageId);
    if (message.isPresent()) {
      return ResponseEntity.ok(message.get());
    } else {
      return null;
    }
  }
    // 6:  delete a message identified by a message ID.
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<?> deleteMessage(@PathVariable Integer messageId) {
      boolean deleted = messageService.deleteMessageById(messageId);
  
      if (deleted) {
     
        return ResponseEntity.ok(1);
      } else {
       
        return ResponseEntity.ok().build();
      }
    }
    // 7:  update a message text identified by a message ID.
  @PatchMapping("/messages/{messageId}")
  public ResponseEntity<?> updateMessage(
      @PathVariable Integer messageId,
      @RequestBody Message updatedMessage) {
    Optional<Integer> updatedRows = messageService.updateMessage(messageId, updatedMessage);

    if (updatedRows.isPresent()) {
      return ResponseEntity.ok(updatedRows.get());
    } else {
      return ResponseEntity.status(400).build();
    }
  }
// 8.  retrieve all messages written by a particular user.
  @GetMapping("/accounts/{accountId}/messages")
  public ResponseEntity<List<Message>> getMessagesByAccountId(@PathVariable Integer accountId) {
    List<Message> messages = messageService.getMessagesByAccountId(accountId);
    return ResponseEntity.ok(messages); 
  }

}
