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

import javax.swing.text.html.Option;
import javax.xml.catalog.Catalog;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {
    private final CatalogRepository repository;

    @KafkaListener(topics="catalog-topic")
    public void updateQty(String kafkaMessage) throws NullPointerException {
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

        Optional<CatalogEntity> entity = repository.findByProductId((String)map.get("productId"));

        log.debug("entity ===> {}" + entity.toString());
        if(!((String) map.get("productId")).isEmpty() && map.get("productId") != null){
            if(entity.isEmpty()){
                CatalogEntity temp = new CatalogEntity(
                        (String) map.get("produceId")
                        , "unmapped-sku"
                        , -(Integer) map.get("qty")
                        , (Integer) map.get("unitPrice")
                );
                repository.save(temp);
            } else {
                CatalogEntity temp = entity.get();
                temp.setStock(temp.getStock() - (Integer) map.get("qty"));
                repository.save(temp);
            }
        }


    }
}