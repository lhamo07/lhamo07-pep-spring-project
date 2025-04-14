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
    Optional<Account> user = accountService.findByAccountId(message.getPostedBy());
    if (!user.isPresent()) {
      return Optional.empty(); 
    }
    return Optional.of(messageRepository.save(message));
  }


  public List<Message> getMessages() {
    return messageRepository.findAll();
  }

  public boolean deleteMessageById(Integer messageId) {
    Optional<Message> message = messageRepository.findByMessageId(messageId);
    if (message.isPresent()) {
      messageRepository.deleteById(messageId);
      return true;
    }
    return false;
  }


  public Optional<Integer> updateMessage(Integer messageId, Message message) {
    Optional<Message> existingMessage = messageRepository.findById(messageId);
    System.out.println("Existing message: " + existingMessage);
    if (existingMessage.isEmpty()) {
      return Optional.empty();
    }

    String messageText = message.getMessageText();
    System.out.println("Message text: " + messageText);
    if (messageText == null || messageText.isBlank() || messageText.length() > 255) {
      return Optional.empty();
    }
    System.out.println("existing message" + existingMessage.get());
    Message messageToUpdate = existingMessage.get();
    messageToUpdate.setMessageText(messageText);

    messageRepository.save(messageToUpdate);
    System.out.println("Updated message: " + messageToUpdate);

    return Optional.of(1); 
  }


  public Optional<Message> getMessageById(Integer messageId) {
    return messageRepository.findByMessageId(messageId);
  }

  
  public List<Message> getMessagesByAccountId(Integer accountId) {
    return messageRepository.findByPostedBy(accountId);
  }

}
