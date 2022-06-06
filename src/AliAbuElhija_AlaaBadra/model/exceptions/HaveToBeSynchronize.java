package AliAbuElhija_AlaaBadra.model.exceptions;

public class HaveToBeSynchronize extends Exception{

    public HaveToBeSynchronize(String name){
        super("Item: " + name + " have to be synchronize!");
    }
}
