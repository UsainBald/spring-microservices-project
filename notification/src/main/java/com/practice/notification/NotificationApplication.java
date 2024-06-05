package com.practice.notification;

import com.practice.notification.orders.OrderPlacedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@SpringBootApplication
public class NotificationApplication {

  public static void main(String[] args) {
    SpringApplication.run(NotificationApplication.class, args);
  }

  @KafkaListener(topics = "notificationTopic")
  public void handleNotification(OrderPlacedEvent orderPlacedEvent) {
    // email functionality
    log.info("Received notification for order: {}", orderPlacedEvent);
  }

}
