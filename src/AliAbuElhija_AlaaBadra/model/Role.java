package AliAbuElhija_AlaaBadra.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import AliAbuElhija_AlaaBadra.model.exceptions.CannotBeChanged;

public class Role implements Serializable, Changeable, Synchronizable{

    private List<Employee> employees;
    private String name;
    private Preference workingMethod;
    private boolean isChangeable;
    private boolean isSynchronized;
    private boolean allowHomeWorking;

    public Role(String name, boolean isChangeable, boolean isSynchronized, boolean allowHomeWorking) {
        this.name = name;
        this.isChangeable = isChangeable;
        this.isSynchronized = isSynchronized;
        this.allowHomeWorking = allowHomeWorking;
        this.employees = new ArrayList<>();
        workingMethod = new Preference();
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public String getName() {
        return name;
    }

    public boolean isChangeable() {
        return isChangeable;
    }

    public boolean isAllowHomeWorking() {
        return allowHomeWorking;
    }

    public double calc(){
        return employees.stream().mapToDouble(Employee::calc).sum();
    }

    @Override
    public boolean canChange() {
//        return isChangeable && employees.stream().filter(Employee::canChange).count() == employees.size();
        return isChangeable;
    }

    @Override
    public Preference getWorkingMethod() {
        return workingMethod;
    }

    @Override
    public void changeWorkingMethod(Preference preference) throws CannotBeChanged {
        if(!canChange())
            throw new CannotBeChanged("Role " + name);
        workingMethod = preference;
        employees.forEach(employee -> employee.changeWorkingMethod(preference));
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("Role: Name: " + name + ", Changeable: " + isChangeable + ", Synchronize: " + isSynchronized + ", Allow working from home: " + allowHomeWorking +", Working Method: " + workingMethod + "\n");
        for (Employee employee : employees) {
            sb.append(employee.toString()).append("\n");
        }
        return  sb.toString();
    }

    public String gainAndLoss(){
        StringBuilder sb = new StringBuilder("Role: Name: " + name + ", Gain(+)/Loss(-): " + calc() + "\n");
        for (Employee employee : employees) {
            sb.append(employee.gainAndLoss()).append("\n");
        }
        return  sb.toString();
    }

    @Override
    public boolean isSynchronize() {
        return isSynchronized;
    }
}
