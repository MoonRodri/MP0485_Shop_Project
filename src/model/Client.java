/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Rodri
 */
import main.Payable;

public class Client extends Person implements Payable {

    private int memberId;
    private Amount balance;

    public static final int MEMBER_ID = 456;
    public static final double BALANCE = 50.00;

    public Client(String name, int memberId, double initialBalance) {
        super(name);
        this.memberId = memberId;
        this.balance = new Amount(initialBalance);
    }

    public int getMemberId() {
        return memberId;
    }

    public Amount getBalance() {
        return balance;
    }

    @Override
    public boolean pay(Amount amount) {
        if (balance.getValue() >= amount.getValue()) {
            balance.subtract(amount);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "cliente: " + getName() + ", ID: " + memberId + ", saldo: " + balance;
    }
}