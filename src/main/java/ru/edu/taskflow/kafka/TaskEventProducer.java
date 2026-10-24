package ru.edu.taskflow.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.edu.taskflow.event.TaskEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class TaskEventProducer {

    private final KafkaTemplate<String , TaskEvent> kafkaTemplate;

    @Value("${app.kafka.topic.task-events}")
    private String topic;

    public void sendEvent(TaskEvent event){

        kafkaTemplate.send(topic,String.valueOf(event.getTaskId()), event);

    }

}
