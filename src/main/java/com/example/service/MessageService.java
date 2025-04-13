package com.example.service;

import java.util.List;
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
    System.out.println("Message: " + message);
    if (message.getMessageText().isBlank()) {

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

  public List<Message> getMessages() {
    return messageRepository.findAll();
  }

  public Optional<Message> getMessageById(Integer messageId) {
    return messageRepository.findByMessageId(messageId);
  }

  public List<Message> getMessagesByAccountId(Integer accountId) {
    return messageRepository.findByPostedBy(accountId);
  }

}
