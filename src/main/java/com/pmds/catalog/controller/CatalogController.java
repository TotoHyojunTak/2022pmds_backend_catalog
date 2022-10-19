package com.backend.order.order.controller;

import com.backend.order.order.data.dto.request.OrderReqDTO;
import com.backend.order.order.data.dto.response.OrderDTO;
import com.backend.order.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/order-service")
public class OrderController {

    private final Environment env;
    private final OrderService orderService;
    //private final KafkaProducer kafkaProducer;


    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in Order Service on PORT %s",
                env.getProperty("local.server.port"));
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<OrderDTO> createOrder(@PathVariable("userId") String userId,
                                                @RequestBody OrderReqDTO orderReqDTO) {
        log.info("Before add orders data");
        orderReqDTO.setUserId(userId);

        /* jpa */
        OrderDTO createdOrder = orderService.createOrder(orderReqDTO);

        /* kafka - start */
        //orderDto.setOrderId(UUID.randomUUID().toString());
        //orderDto.setTotalPrice(orderDetails.getQty() * orderDetails.getUnitPrice());

        /* send this order to the kafka */
        //kafkaProducer.send("example-catalog-topic", orderDto);
        //orderProducer.send("orders", orderDto);
        //ResponseOrder responseOrder = mapper.map(orderDto, ResponseOrder.class);
        /* kafka - end */

        log.info("After added orders data");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<OrderDTO>> getOrder(@PathVariable("userId") String userId) throws Exception {
  /*
        List<OrderEntity> orderList = orderService.getOrdersByUserId(userId);

        List<OrderDTO> result = new ArrayList<>();
        orderList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseOrder.class));
        });
*/


        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrdersByUserId(userId));
    }
}