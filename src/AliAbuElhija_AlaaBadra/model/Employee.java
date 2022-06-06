package AliAbuElhija_AlaaBadra.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Employee implements Serializable, Changeable{

    private static long idGenerator = 1000;
    private String name;
    private long id;
    protected int salary;
    private Preference preference;
    private Preference workingMethod;
    private boolean isChangeable;
    private boolean canWorkFromHome;

    public Employee(String name, Preference preference, boolean isChangeable, boolean canWorkFromHome, int salary) {
        id = idGenerator++;
        this.name = name;
        this.preference = preference;
        this.isChangeable = isChangeable;
        this.canWorkFromHome = canWorkFromHome;
        this.salary = salary;
        workingMethod = new Preference();
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public Preference getPreference() {
        return preference;
    }

    public double calc(){
        if(canWorkFromHome && preference.isFromHome())
            return 0.8 * salary;

        if(workingMethod.isStandard() && preference.isStandard())
            return 0;

        /*
        Set<Integer> prefWorkingHoers = new HashSet<>();
        int preferStartingHoer = preference.getStartingHoer();
        for (int i = preferStartingHoer; i < preferStartingHoer + 10; i++) {
            prefWorkingHoers.add(i);
        }

        Set<Integer> workingHoers = new HashSet<>();
        int workStartingHoer = workingMethod.getStartingHoer();
        for (int i = workStartingHoer; i < workStartingHoer + 10; i++) {
            workingHoers.add(i);
        }

        Set<Integer> retain = new HashSet<>(prefWorkingHoers);
        retain.retainAll(workingHoers);
        int retainSize = retain.size();

        double res;
        if(retainSize > 8)
            res = 0.2 * 8 * salary;
        else if(retainSize == 0)
            res = -0.2 * 8 * salary;
        else
            res = (retainSize * 0.2 + (8 - retainSize) * (-0.2)) * salary;
        return res;*/

        // Set A - prefer hoers
        Set<Integer> a = new HashSet<>();
        int preferStartingHoer = preference.getStartingHoer();
        for (int i = preferStartingHoer; i < preferStartingHoer + 10; i++) {
            a.add(i);
        }

        // Set B - working hoers
        Set<Integer> b = new HashSet<>();
        int workStartingHoer = workingMethod.getStartingHoer();
        for (int i = workStartingHoer; i < workStartingHoer + 10; i++) {
            b.add(i);
        }

        // Set C - standard hoers
        Set<Integer> c = new HashSet<>();
        for (int i = 8; i < 18; i++) {
            c.add(i);
        }

        Set<Integer> abc = new HashSet<>(a);
        abc.retainAll(b);
        abc.retainAll(c);
        int abcSize = abc.size();

        Set<Integer> ab = new HashSet<>(a);
        ab.retainAll(b);
        int abSize = ab.size();

//        double res = (abSize - abcSize) * 0.2;
//        res += (b.size() - abSize - bcSize + abcSize) * -0.2;

        double res = Math.min((abSize - abcSize), 8) * 0.2;
        res += Math.min((b.size() - abSize), 8) * (-0.2);
        if((abSize - abcSize) + (b.size() - abSize) > 8)
            res += 0.2;

        return res * salary;
    }

    @Override
    public boolean canChange() {
        return isChangeable;
    }

    @Override
    public Preference getWorkingMethod() {
        return workingMethod;
    }

    @Override
    public void changeWorkingMethod(Preference preference) {
        workingMethod = preference;
    }

    @Override
    public String toString(){
        return "Employee: ID: " + id + ", Name: " + name + ", Prefer To Work " + preference + ", Salary: " + salary;
    }

    public String gainAndLoss(){
        return "Employee: ID: " + id + ", Name: " + name + ", Gain(+)/Loss(-): " + calc();
    }
}
