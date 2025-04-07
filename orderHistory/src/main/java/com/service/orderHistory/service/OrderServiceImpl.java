package com.service.orderHistory.service;

import com.service.orderHistory.bean.OrderProduct;
import com.service.orderHistory.bean.Orders;
import com.service.orderHistory.bean.Product;
import com.service.orderHistory.dao.OrderProductRepository;
import com.service.orderHistory.dto.OrderDto;
import com.service.orderHistory.dto.ProductDto;
import com.service.orderHistory.dao.OrderRepository;
import com.service.orderHistory.dao.ProductRepository;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Override
    public ResponseEntity<String> createOrder(List<ProductDto> orderRequest) throws BadRequestException {

        if(null!=orderRequest && !orderRequest.isEmpty()){
            Map<Integer, Integer> prodIdToQuant = orderRequest.stream()
                    .collect(Collectors.toMap(ProductDto::getProductId, ProductDto::getQuantity));
            List<Product> products = productRepository.findAllById(prodIdToQuant.keySet());
            double totalAmount = 0;

            Orders order = new Orders();
            order.setOrderDate(LocalDate.now());

            order.setStatus("Completed");
            if(!products.isEmpty()){
                for(Product product : products){
                    OrderProduct orderProduct = new OrderProduct();
                    orderProduct.setOrder(order);
                    orderProduct.setProduct(product);

                    Integer quantity = prodIdToQuant.get(product.getId());
                    orderProduct.setQuantity(quantity);
                    totalAmount = totalAmount+ (product.getPrice() * quantity);
                    orderProductRepository.save(orderProduct);
                }
                order.setTotalAmount(totalAmount);

                orderRepository.save(order);
            }else{
                throw new BadRequestException("Product details are missing");
            }

            return new ResponseEntity<>("Order Created", HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>("Please add product details to place an order.", HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public List<OrderDto> getOrdersHistory() {
        List<Orders> orderList = orderRepository.findAll();
        return convertOrdersToOrderResponse(orderList);
    }

    private List<OrderDto> convertOrdersToOrderResponse(List<Orders> orderList) {
        List<OrderDto> orderResponse = new ArrayList<>();
        for(Orders order:orderList){
            OrderDto orderDto = new OrderDto();
            orderDto.setId(order.getId());
            orderDto.setOrderDate(order.getOrderDate());
            orderDto.setTotalAmount(order.getTotalAmount());
            orderDto.setStatus(order.getStatus());

            List<ProductDto> productDtos = new ArrayList<>();
            for (Product product: order.getProducts()){
                ProductDto productDto = new ProductDto();
                productDto.setProductId(product.getId());
                productDto.setName(product.getName());
                Integer quantity = orderProductRepository.findQuantityByOrderAndProductId(order.getId(), product.getId());
                productDto.setQuantity(quantity);
                productDtos.add(productDto);
            }

            orderDto.setItems(productDtos);

            orderResponse.add(orderDto);
        }

        return orderResponse;
    }
}
