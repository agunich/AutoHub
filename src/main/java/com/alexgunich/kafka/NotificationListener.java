//package com.alexgunich.kafka;
//
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//@Service
//public class NotificationListener {
//
//    @KafkaListener(topics = "notifications-topic", groupId = "my-consumer-group")
//    public void listen(String message) {
//        System.out.println("Received notification: " + message);
//    }
//}
