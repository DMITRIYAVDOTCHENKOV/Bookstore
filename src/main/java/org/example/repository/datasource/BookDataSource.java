package org.example.repository.datasource;

import org.example.model.Book;
import org.example.model.Customer;
import org.example.model.Employee;
import org.example.model.Order;

import java.util.ArrayList;
import java.util.List;

public interface BookDataSource {
    List<Book> getBooks();
    List<Customer> getCustomers();
    List<Employee> getEmployees();
    List<Order> getOrders();

}
