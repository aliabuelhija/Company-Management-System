package AliAbuElhija_AlaaBadra.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import AliAbuElhija_AlaaBadra.listeners.CompanyEventListener;
import AliAbuElhija_AlaaBadra.listeners.CompanyUIEventListener;
import AliAbuElhija_AlaaBadra.model.*;
import AliAbuElhija_AlaaBadra.model.exceptions.CannotBeChanged;
import AliAbuElhija_AlaaBadra.model.exceptions.HaveToBeSynchronize;
import AliAbuElhija_AlaaBadra.model.exceptions.NameAlreadyExists;
import AliAbuElhija_AlaaBadra.view.CompanyJavaFX;

public class CompanyController implements CompanyUIEventListener, CompanyEventListener {

    private Company company;
    private CompanyJavaFX companyView;

    public CompanyController(Company company, CompanyJavaFX companyView) {
        this.company = company;
        this.companyView = companyView;
        companyView.registerListener(this);
    }

    @Override
    public void addEmployeeToUIEvent(String name, Preference preference, String departmentName, String roleName, int salary, boolean addBonus) {
        Department department = company.getDepartmentByName(departmentName);
        Role role = department.getRoleByName(roleName);
        if(addBonus) {
            int bonus = (company.getMonthlySells() * company.getMonthlyBonusPercent()) / 100;
            role.addEmployee(new EmployeeWithBonus(name, preference, true, role.isAllowHomeWorking(), salary, bonus));
        }
        else
            role.addEmployee(new Employee(name, preference, true, role.isAllowHomeWorking(), salary));
        addEmployeeToModelEvent("OK","Employee: " + name + " was added!");
        System.out.println("Employee: " + name + " was added!");

    }

    @Override
    public void addRoleToUIEvent(String name, boolean isChangeable, boolean isSynchronized, boolean allowHomeWorking, String departmentName) {
        try {
            Department department = company.getDepartmentByName(departmentName);
            if(department.isSynchronize() && !isSynchronized)
                throw new HaveToBeSynchronize(name);
            department.addRole(new Role(name, isChangeable, isSynchronized, allowHomeWorking));
            addRuleToModelEvent("OK", "Role " + name + " was added!");
            System.out.println("Role " + name + " was added!");
        } catch (NameAlreadyExists nameAlreadyExists) {
            addRuleToModelEvent("ERROR","Role name: " + name + " is already exists!");
            System.out.println("Role name: " + name + " is already exists!");
        } catch (HaveToBeSynchronize haveToBeSynchronize) {
            addRuleToModelEvent("ERROR","Role: " + name + " have to be synchronise as it's department");
            System.out.println("Role: " + name + " have to be synchronise as it's department");
        }
    }

    @Override
    public void addDepartmentToUIEvent(String name, boolean isChangeable, boolean isSynchronize) {
        try {
            Department department = new Department(name, isChangeable, isSynchronize);
            company.addDepartment(department);
            addDepartmentToModelEvent("OK","Department: " + name + " was added!");
            System.out.println("Department: " + name + " was added!");
        } catch (NameAlreadyExists nameAlreadyExists) {
            addDepartmentToModelEvent("ERROR","Department name: " + name + " is already exists!");
            System.out.println("Department name: " + name + " is already exists!");
        }
    }

    @Override
    public void exitToUIEvent() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Company.data"));
            out.writeObject(company);
            out.close();
            System.out.println("Data saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Department> getDepartmentsToUIEvent() {
        return company.getDepartments();
    }

    @Override
    public List<Role> getRolesByDepartmentNameToUIEvent(String departmentName) {
        Department department = company.getDepartmentByName(departmentName);
        return department.getRoles();
    }

    @Override
    public void showDetailsToUIEvent() {
        showDetailsToModelEvent(company.getDepartments());
    }

    @Override
    public void changeRolePreferenceToUIEvent(int delta, String departmentName, String roleName) {
        Department department = company.getDepartmentByName(departmentName);
        if(department.isSynchronize()){
            changeRolePreferenceToModelEvent("ERROR","Cannot change Role because its department is synchronize");
            System.out.println("Cannot change Role " + roleName + " because its department is synchronize");
            return;
        }
        try {
            Role role = department.getRoleByName(roleName);
            role.changeWorkingMethod(new Preference(role.getWorkingMethod().getStartingHoer() + delta));
            changeRolePreferenceToModelEvent("OK","Role " + roleName + " preference changed!");
            System.out.println("Role " + roleName + " preference changed!");
        } catch (CannotBeChanged cannotBeChanged) {
            changeRolePreferenceToModelEvent("ERROR","Role Cannot be changed.");
            System.out.println("Role " + roleName + " Cannot be changed.");
        }
    }

    @Override
    public void changeDepartmentPreferenceToUIEvent(int delta, String departmentName) {
        try {
            Department department = company.getDepartmentByName(departmentName);
            department.changeWorkingMethod(new Preference(department.getWorkingMethod().getStartingHoer() + delta));
            changeDepartmentPreferenceToModelEvent("OK","Department " + departmentName + " preference changed!");
            System.out.println("Department " + departmentName + " preference changed!");
        } catch (CannotBeChanged cannotBeChanged) {
            changeDepartmentPreferenceToModelEvent("ERROR","Department Cannot be changed.");
            System.out.println("Department " + departmentName + " Cannot be changed.");
        }
    }

    @Override
    public void showGainAndLossToUIEvent() {
        showGainAndLossToModelEvent(company.getDepartments());
    }

    @Override
    public void saveBonusToUIEvent(int monthlySells, int monthlyBonusPercent) {
        company.setMonthlySells(monthlySells);
        company.setMonthlyBonusPercent(monthlyBonusPercent);
        company.updateEmployeesBonus();
    }

    @Override
    public void addDepartmentToModelEvent(String response, String msg) {
        companyView.showAddDepartmentResponse(response, msg);
    }

    @Override
    public void addRuleToModelEvent(String response, String msg) {
        companyView.showAddRuleResponse(response, msg);
    }

    @Override
    public void addEmployeeToModelEvent(String response, String msg) {
        companyView.showAddEmployeeResponse(response, msg);
    }

    @Override
    public void showDetailsToModelEvent(List<Department> departments) {
        companyView.showDetails(departments);
    }

    @Override
    public void showGainAndLossToModelEvent(List<Department> departments) {
        companyView.showGainAndLoss(departments);
    }

    @Override
    public void changeRolePreferenceToModelEvent(String response, String msg) {
        companyView.showChangeRolePreferenceResponse(response, msg);
    }

    @Override
    public void changeDepartmentPreferenceToModelEvent(String response, String msg) {
        companyView.showChangeDepartmentPreferenceResponse(response, msg);
    }

}
