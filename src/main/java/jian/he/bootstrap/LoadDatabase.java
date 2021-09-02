package jian.he.bootstrap;

import jian.he.payroll.Employee;
import jian.he.payroll.Order;
import jian.he.payroll.Status;
import jian.he.repositories.EmployeeRepository;
import jian.he.repositories.OrderRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository employeeRepository, OrderRepository orderRepository){
        return args -> {
                    employeeRepository.save(
                            new Employee("Bilbo","Baggins","burglar"));
                    employeeRepository.save(
                            new Employee("Frodo","Freddie","thief"));
                    employeeRepository.findAll().forEach(employee -> log.info("Preloaded " + employee));

                    orderRepository.save(new Order("MacBook Pro", Status.COMPLETED));
                    orderRepository.save(new Order("iPhone",Status.IN_PROGRESS));

                    orderRepository.findAll().forEach(employee -> log.info("Preloaded " + employee));
        };

    }

}
