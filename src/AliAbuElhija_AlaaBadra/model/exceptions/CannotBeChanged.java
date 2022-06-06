package AliAbuElhija_AlaaBadra.model.exceptions;

public class CannotBeChanged extends Exception{

    public CannotBeChanged(String name){
        super("Item: " + name + " Cannot be changed");
    }
}
