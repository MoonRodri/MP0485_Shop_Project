/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Rodri
 */
import main.Logable;

public class Employee extends Person implements Logable {

    private int employeeId;
    private String password;

    public static final int EMPLOYEE_ID = 123;
    public static final String PASSWORD = "test";

    public Employee(String name, int employeeId, String password) {
        super(name);
        this.employeeId = employeeId;
        this.password = password;
    }

    @Override
    public boolean login(int user, String pass) {
        return this.employeeId == user && this.password.equals(pass);
    }

    @Override
    public String toString() {
        return "empleado: " + getName() + ", ID: " + employeeId;
    }
}