package it.nextworks.tmf_offering_catalog.services;

import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrder;
import it.nextworks.tmf_offering_catalog.information_models.product.order.ProductOrderCreate;
import it.nextworks.tmf_offering_catalog.repo.ProductOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductOrderService {

    private static final Logger log = LoggerFactory.getLogger(ProductOrderService.class);

    private static final String protocol = "http://";
    @Value("${server.hostname}")
    private String hostname;
    @Value("${server.port}")
    private String port;
    private static final String path = "/tmf-api/productOrderingManagement/v4/productOrder/";

    @Autowired
    private ProductOrderRepository productOrderRepository;

    @Transactional
    public ProductOrder create(ProductOrderCreate productOrderCreate) {
        log.info("Received request to create a Product Order.");
        ProductOrder productOrder = productOrderRepository.save(new ProductOrder(productOrderCreate));
        productOrder.href(protocol + hostname + ":" + port + path + productOrder.getId());

        log.info("Product Order created with id " + productOrder.getId() + ".");
        return productOrder;
    }

    @Transactional
    public List<ProductOrder> list() {
        log.info("Received request to retrieve all Product Orders");
        return productOrderRepository.findAll();
    }
}
