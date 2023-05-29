package cn.itcast.order.service;


import cn.itcast.feign.clients.UserClient;
import cn.itcast.feign.pojo.User;
import cn.itcast.order.mapper.OrderMapper;
import cn.itcast.order.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserClient userClient;
    public Order queryOrderById(Long orderId) {
        Order order = orderMapper.findById(orderId);
        // 2.用Feign远程调用
        User user = userClient.fingById(order.getUserId());
        // 3.封装user到order
        order.setUser(user);
        // 4.返回
        return order;
    }

  /*  public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);
        // 2.url路径
        String url="http://userservice/user/"+ order.getUserId();
        // 2.发送http请求
        User user = restTemplate.getForObject(url, User.class);
        // 3.封装user到order
        order.setUser(user);
        // 4.返回
        return order;
    }*/
}
