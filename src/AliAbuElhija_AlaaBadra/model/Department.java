package AliAbuElhija_AlaaBadra.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import AliAbuElhija_AlaaBadra.model.exceptions.CannotBeChanged;
import AliAbuElhija_AlaaBadra.model.exceptions.NameAlreadyExists;

public class Department implements Serializable, Changeable, Synchronizable{

    private String name;
    private List<Role> roles;
    private boolean isChangeable;
    private boolean isSynchronized;
    private Preference workingMethod;

    public Department(String name, boolean isChangeable, boolean isSynchronized) {
        this.name = name;
        this.isChangeable = isChangeable;
        this.isSynchronized = isSynchronized;
        roles = new ArrayList<>();
        workingMethod = new Preference();
    }

    public String getName() {
        return name;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void addRole(Role role) throws NameAlreadyExists {
        if(roles.stream().anyMatch(d -> d.getName().equalsIgnoreCase(role.getName()))){
            throw new NameAlreadyExists(role.getName());
        }
        roles.add(role);
    }

    @Override
    public boolean isSynchronize() {
        return isSynchronized;
    }

    public double calc(){
        return roles.stream().mapToDouble(Role::calc).sum();
    }

    @Override
    public boolean canChange() {
        if(isChangeable){
            if(isSynchronized){
                return roles.stream().allMatch(Role::canChange);
            }
            return true;
        }
        return false;
    }

    @Override
    public Preference getWorkingMethod() {
        return workingMethod;
    }

    @Override
    public void changeWorkingMethod(Preference preference) throws CannotBeChanged {
        if(!canChange())
            throw new CannotBeChanged("Department " + name);
        for (Role role : roles) {
            role.changeWorkingMethod(preference);
        }
        workingMethod = preference;
    }

    public Role getRoleByName(String name) {
        return roles.stream().filter(r -> r.getName().equals(name)).findAny().get();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("Department: Name: " + name + ", Changeable: " + isChangeable + ", Synchronize: " + isSynchronized + ", Working Method: " + workingMethod +"\n");
        for (Role role : roles) {
            sb.append(role.toString());
        }
        sb.append("\n");
        return sb.toString();
    }

    public String gainAndLoss(){
        StringBuilder sb = new StringBuilder("Department: Name: " + name + ", Gain(+)/Loss(-): " + calc() + "\n");
        for (Role role : roles) {
            sb.append(role.gainAndLoss());
        }
        sb.append("\n");
        return sb.toString();
    }
}
