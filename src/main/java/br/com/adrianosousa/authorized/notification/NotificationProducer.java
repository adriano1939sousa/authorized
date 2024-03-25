package br.com.adrianosousa.authorized.notification;


import br.com.adrianosousa.authorized.transaction.Transaction;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {

  //  private final KafkaTemplate<String, Transaction> kafkaTemplate;

    public void sendNotification(Transaction transaction){
  //      kafkaTemplate.send("transaction-notification", transaction);
    }
}
