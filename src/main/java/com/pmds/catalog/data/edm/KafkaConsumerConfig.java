package com.pmds.catalog.data.edm;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmds.catalog.data.entity.CatalogEntity;
import com.pmds.catalog.data.repository.CatalogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {
    private final CatalogRepository repository;

    @KafkaListener(topics="example-catalog-topic")
    public void updateQty(String kafkaMessage){
        log.info("kafka msg : " + kafkaMessage);

        // 역직렬화
        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try{
            map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        CatalogEntity entity = repository.findByProductId((String)map.get("productId"));
        if(entity!=null){
            entity.setStock(entity.getStock() - (Integer) map.get("qty"));
            repository.save(entity);
        }

    }
}