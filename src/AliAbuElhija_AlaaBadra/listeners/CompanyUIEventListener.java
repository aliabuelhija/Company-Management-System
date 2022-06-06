package AliAbuElhija_AlaaBadra.listeners;

import java.util.List;

import AliAbuElhija_AlaaBadra.model.Department;
import AliAbuElhija_AlaaBadra.model.Preference;
import AliAbuElhija_AlaaBadra.model.Role;

public interface CompanyUIEventListener {

    void addEmployeeToUIEvent(String name, Preference preference, String departmentName, String roleName, int salary, boolean addBonus);

    void addRoleToUIEvent(String name, boolean isChangeable, boolean isSynchronized, boolean allowHomeWorking, String departmentName);

    void addDepartmentToUIEvent(String name, boolean isChangeable, boolean isSynchronize);

    void exitToUIEvent();

    List<Department> getDepartmentsToUIEvent();

    List<Role> getRolesByDepartmentNameToUIEvent(String departmentName);

    void showDetailsToUIEvent();

    void changeRolePreferenceToUIEvent(int delta, String departmentName, String roleName);

    void changeDepartmentPreferenceToUIEvent(int delta, String departmentName);

    void showGainAndLossToUIEvent();

    void saveBonusToUIEvent(int monthlySells, int monthlyBonusPercent);
}
