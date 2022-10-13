package ro.tuc.dsrl.ds.handson.assig.three.producer.start;

import com.google.gson.Gson;
import com.rabbitmq.client.*;
import ro.tuc.dsrl.ds.handson.assig.three.producer.model.DVD;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    private final static String QUEUE_NAME = "dvd_queue";

    public static void produce(DVD dvd) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //DVD dvd = new DVD("ceva", 1,1 );
        Gson gson = new Gson();
        String message = gson.toJson(dvd);
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }
}
