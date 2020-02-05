package com.template.coe.demo.repository;

import com.template.coe.demo.domain.Customer;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class CustomerRepository {
    List<Customer> customers = new CopyOnWriteArrayList<>(Arrays.asList(
            new Customer(1L, "kido"),
            new Customer(2L, "kido2"),
            new Customer(3L, "kido3")
    ));

    public Collection<Customer> findAll() {

        return customers;
    }

    public Optional<Customer> findById(Long id) {
        return customers.stream().filter(item -> item.getId() == id).findFirst();
    }

    public Customer save(Customer c) {

        Optional<Customer> byId = this.findById(c.getId());
        if (byId.isEmpty()) {
            Customer customer = customers.get(customers.size() - 1);
            Customer newCustomer = new Customer(customer.getId() + 1, c.getName());
            customers.add(newCustomer);
            return newCustomer;
        }
        else {
            Customer oldCustomer = byId.get();
            customers.remove(oldCustomer);
            oldCustomer.setName(c.getName());
            customers.add(oldCustomer);
            return oldCustomer;
        }
    }

    public void delete(Customer c) {
        customers.remove(c);
    }
}
