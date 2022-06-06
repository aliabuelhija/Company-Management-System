package AliAbuElhija_AlaaBadra.model.exceptions;

public class NameAlreadyExists extends Exception{

    public NameAlreadyExists(String name){
        super("Name " + name + " is already exists.");
    }
}
