package AliAbuElhija_AlaaBadra.model;

import AliAbuElhija_AlaaBadra.model.exceptions.CannotBeChanged;

public interface Changeable {

    boolean canChange();

    Preference getWorkingMethod();

    void changeWorkingMethod(Preference preference) throws CannotBeChanged;
}
