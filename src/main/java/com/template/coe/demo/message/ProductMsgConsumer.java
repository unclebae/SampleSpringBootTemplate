package com.template.coe.demo.message;

import com.template.coe.demo.domain.Product;
import com.template.coe.demo.domain.ProductUpdMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductMsgConsumer {

    @JmsListener(destination = "${jms.ProductTopic}", subscription = "productSearchListener")
    public void receiveMessage(ProductUpdMsg msg) {
        Product product = msg.getProduct();
        boolean isDeleted = msg.isDelete();
        if (isDeleted) {
            log.info("Delete message received: ");
            log.info("Prouct : " + product);
//            CQRS 이므로 이제 외부에서 메시지를 수신하고, 검색관점의 모델을 업데이트한다.
        }
        else {
            log.info("Another message received: ");
            log.info("Prouct : " + product);
//            CQRS 이므로 이제 외부에서 메시지를 수신하고, 검색관점의 모델을 업데이트한다.
        }

    }
}
