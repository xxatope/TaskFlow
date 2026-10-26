package ru.edu.taskflow.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.edu.taskflow.event.TaskEvent;

@Component
public class TaskEventProducer {

    private static final Logger log = LoggerFactory.getLogger(TaskEventProducer.class);

    private final KafkaTemplate<String, TaskEvent> kafkaTemplate;

    @Value("${app.kafka.topic.task-events}")
    private String topic;

    public TaskEventProducer(KafkaTemplate<String, TaskEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(TaskEvent event) {
        log.info("Sending task event: {}", event);
        kafkaTemplate.send(topic, String.valueOf(event.taskId()), event);
    }
}
