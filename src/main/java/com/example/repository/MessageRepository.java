package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {

  Optional<Message> findByMessageId(Integer messageId);

  List<Message> findByPostedBy(Integer accountId);
}
