package AliAbuElhija_AlaaBadra.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import AliAbuElhija_AlaaBadra.model.exceptions.NameAlreadyExists;

public class Company implements Serializable {

    private List<Department> departments;
    private int monthlySells;
    private int monthlyBonusPercent;

    public Company() {
        this.departments = new ArrayList<>();
        monthlySells = 0;
        monthlyBonusPercent = 0;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public int getMonthlySells() {
        return monthlySells;
    }

    public void setMonthlySells(int monthlySells) {
        this.monthlySells = monthlySells;
    }

    public int getMonthlyBonusPercent() {
        return monthlyBonusPercent;
    }

    public void setMonthlyBonusPercent(int monthlyBonusPercent) {
        this.monthlyBonusPercent = monthlyBonusPercent;
    }

    public Department getDepartmentByName(String name){
        return departments.stream().filter(d -> d.getName().equals(name)).findAny().get();
    }

    public void addDepartment(Department department) throws NameAlreadyExists {
        if(departments.stream().anyMatch(d -> d.getName().equalsIgnoreCase(department.getName()))){
            throw new NameAlreadyExists(department.getName());
        }
        departments.add(department);
    }

    public void updateEmployeesBonus() {
        for (Department department : departments) {
            List<Role> roles = department.getRoles();
            for (Role role : roles) {
               List<Employee> employees = role.getEmployees();
                for (Employee employee : employees) {
                    if(employee instanceof EmployeeWithBonus)
                        ((EmployeeWithBonus)employee).setBonus(calcBonus());
                }
            }
        }
    }

    private int calcBonus() {
        return monthlySells * (monthlyBonusPercent / 100);
    }
}
