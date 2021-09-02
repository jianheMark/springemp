package jian.he.controllers;

import jian.he.payroll.Order;
import jian.he.payroll.Status;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {

    @Override
    // Unconditional links to single-item resource and aggregate root
    public EntityModel<Order> toModel(Order order) {
        EntityModel<Order> orderModel = EntityModel.of(order,
                linkTo(methodOn(OrderController.class).FindOneOrderByOrderId(order.getId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).FindAllOrder()).withRel("orders"));

        if(order.getStatus() == Status.IN_PROGRESS) {
            orderModel.add(linkTo(methodOn(OrderController.class).cancel(order.getId())).withRel("cancel"));
            orderModel.add(linkTo(methodOn(OrderController.class).complete(order.getId())).withRel("complete"));
        }


        return orderModel;
    }
}
