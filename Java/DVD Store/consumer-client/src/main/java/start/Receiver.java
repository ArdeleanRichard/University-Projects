package ro.tuc.dsrl.ds.handson.assig.three.consumer.start;

import com.google.gson.Gson;
import com.rabbitmq.client.*;
import ro.tuc.dsrl.ds.handson.assig.three.consumer.service.MailService;
import ro.tuc.dsrl.ds.handson.assig.three.producer.model.DVD;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class Receiver {
    private static final String QUEUE_NAME = "dvd_queue";

    public static void main (String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        final MailService mailService = new MailService("your_account_here","your_password_here");

        final List<String> subscribers = new ArrayList<>();
        subscribers.add("rici");
        subscribers.add("andreea");
        subscribers.add("flaviu");
        subscribers.add("victor");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                Gson g = new Gson();
                DVD dvd = g.fromJson(message, DVD.class);
                System.out.println(" [x] Received '" + dvd + "'");

                for(String subscriber: subscribers)
                    mailService.sendMail(subscriber,"New DVD",message);
                int i=0;
                BufferedWriter writer = new BufferedWriter(new FileWriter("file"+(i++)+".txt", true));
                writer.append(message);
                writer.close();
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
