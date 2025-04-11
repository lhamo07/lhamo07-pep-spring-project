package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
  MessageRepository messageRepository;
  AccountService accountService;

  @Autowired
  public MessageService(MessageRepository messageRepository, AccountService accountService) {
    this.accountService = accountService;
    this.messageRepository = messageRepository;

  }

  public Optional<Message> createdMessage(Message message) {
    System.out.println("MessageService: " + message);
    if (message.getMessageText().isEmpty()) {
      return Optional.empty();
    }
    if (message.getMessageText().length() > 255) {
      return Optional.empty();
    }
    // Validate postedBy refers to a real user
    Optional<Account> user = accountService.findByAccountId(message.getPostedBy());
    if (!user.isPresent()) {
      return Optional.empty(); // User doesn't exist
    }
    return Optional.of(messageRepository.save(message));
  }
}
