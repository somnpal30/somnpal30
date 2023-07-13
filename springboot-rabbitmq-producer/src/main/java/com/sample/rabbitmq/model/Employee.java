package com.sample.rabbitmq.model;

import java.io.Serializable;

public class Employee implements Serializable {


    private static final long serialVersionUID = -2792967979167569598L;
    private String empName;
    private String empId;


    public Employee(String empName, String empId) {
        this.empName = empName;
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    @Override
    public String toString() {
        return "Employee{" + "empName='" + empName + '\'' + ", empId='" + empId + '\'' + '}';
    }
}
