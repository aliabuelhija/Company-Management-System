package AliAbuElhija_AlaaBadra.listeners;

import java.util.List;

import AliAbuElhija_AlaaBadra.model.Department;

public interface CompanyEventListener {

    void addDepartmentToModelEvent(String response, String msg);

    void addRuleToModelEvent(String response, String msg);

    void addEmployeeToModelEvent(String response, String msg);

    void showDetailsToModelEvent(List<Department> departments);

    void showGainAndLossToModelEvent(List<Department> departments);

    void changeRolePreferenceToModelEvent(String response, String msg);

    void changeDepartmentPreferenceToModelEvent(String response, String msg);
}
