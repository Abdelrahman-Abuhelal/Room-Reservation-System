package com.example.roomreservation.service;

import com.example.roomreservation.exception.EmployeeNotFoundException;
import com.example.roomreservation.model.Employee;
import com.example.roomreservation.model.EmployeeDTO;
import com.example.roomreservation.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    public Employee createEmployee(EmployeeDTO employee){
        Employee createdEmployee= modelMapper.map(employee, Employee.class);
        return employeeRepository.save(createdEmployee);
    }

    public Employee getEmployeeById(Long id){
        return employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id " + id));
    }

    public Employee updateEmployee(Long id, EmployeeDTO employeeDTO){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id " + id));

        modelMapper.map(employeeDTO, employee);
        employee.setId(id);
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id){
        employeeRepository.deleteById(id);
    }
}
