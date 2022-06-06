package AliAbuElhija_AlaaBadra.model;

import java.io.Serializable;

public class EmployeeWithBonus extends Employee implements Serializable {

    private int bonus;

    public EmployeeWithBonus(String name, Preference preference, boolean isChangeable, boolean canWorkFromHome, int salary, int bonus) {
        super(name, preference, isChangeable, canWorkFromHome, salary);
        this.bonus = bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
   }

    @Override
    public double calc(){
        this.salary += bonus;
        double res = salary;
        return res;
    }

    public String toString(){
        String employeeStr = super.toString();
        return employeeStr + ", Bonus: " + bonus;
    }
}
