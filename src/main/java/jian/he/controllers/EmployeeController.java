package jian.he.controllers;

import jian.he.commons.EmployeeNotFoundException;
import jian.he.payroll.Employee;
import jian.he.repositories.EmployeeRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
//@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeRepository employeeRepository;
    private final EmployeeModelAssembler employeeModelAssembler;

    public EmployeeController(EmployeeRepository employeeRepository,
                              EmployeeModelAssembler employeeModelAssembler) {
        this.employeeRepository = employeeRepository;
        this.employeeModelAssembler = employeeModelAssembler;
    }

    //aggregate root//tag::get-aggregate-rot[]
    @GetMapping("/employees")
    public CollectionModel<EntityModel<Employee>> getAllemps(){
        List<EntityModel<Employee>> employees = employeeRepository.findAll().stream()
                .map(employeeModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(
                employees,linkTo(methodOn(EmployeeController.class).getAllemps()).withSelfRel());
    }

    @PostMapping("/employees")
    ResponseEntity<?> newEmployee(@RequestBody Employee newEmployee){
        EntityModel<Employee> entityModel =
                employeeModelAssembler.toModel(employeeRepository.save(newEmployee));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
    //find out single Emp.
    @GetMapping("/employees/{id}")
    public EntityModel<Employee> FindOneEmp(@PathVariable Long id){
        Employee employee =  employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        return employeeModelAssembler.toModel(employee);
    }

    @PutMapping("/employees/{id}")
    ResponseEntity<?> UpdateEmployee(@RequestBody Employee newEmployee, @PathVariable Long id){
        Employee updatedEmployee = employeeRepository.findById(id)
                .map(employee -> {
                    employee.setRole(newEmployee.getRole());
                    employee.setName(newEmployee.getName());
                    return employeeRepository.save(employee);
                })
                .orElseGet(() ->{
                    newEmployee.setId(id);
                    return employeeRepository.save(newEmployee);
                });
        EntityModel<Employee> entityModel= employeeModelAssembler.toModel(updatedEmployee);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/employees/{id}")
    ResponseEntity<?> DeleteEmployees(@PathVariable Long id){
        employeeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
