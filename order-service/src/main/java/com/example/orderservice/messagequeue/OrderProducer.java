package com.example.orderservice.messagequeue;


import com.example.orderservice.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class OrderProducer {

    private KafkaTemplate<String,String> kafkaTemplate;

    List<Field> fields = Arrays.asList(
            new Field("string",true,"order_id"),
            new Field("string",true,"product_id"),
            new Field("string", true, "user_id"),    // 👈 필수 추가: 이게 없어서 DB 인서트가 안 됨
            new Field("int32", true, "qty"),         // 👈 int 타입으로 변경 권장
            new Field("int32", true, "unit_price"),  // 👈 int 타입으로 변경 권장
            new Field("int32", true, "total_price")  // 👈 int 타입으로 변경 권장
            );
    Schema schema = Schema.builder()
            .type("struct")
            .fields(fields)
            .optional(false)
            .name("orders").build();

    @Autowired
    private OrderProducer(KafkaTemplate<String,String> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }




    public OrderDto send(String topic, OrderDto orderDto){
        Payload payload = Payload.builder()
                .order_id(orderDto.getOrderId())
                .user_id(orderDto.getUserId())
                .product_id(orderDto.getProductId())
                .qty(orderDto.getQty())
                .unit_price(orderDto.getUnitPrice())
                .total_price(orderDto.getTotalPrice())
                .build();
        KafkaOrderDto kafkaOrderDto = new KafkaOrderDto(schema,payload);

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString  = "";
        try {
            jsonInString = mapper.writeValueAsString(kafkaOrderDto);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        kafkaTemplate.send(topic,jsonInString);
        log.info("Kafka Producer sent data from order MSA "+ orderDto);
        return orderDto;
    }


}
