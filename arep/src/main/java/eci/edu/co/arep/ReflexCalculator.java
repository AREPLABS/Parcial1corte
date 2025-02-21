package eci.edu.co.arep;

public class ReflexCalculator {

    
    public ReflexCalculator reflexCalculator;

    public ReflexCalculator getReflexCalculator(){
        if (reflexCalculator== null) {
            reflexCalculator = new ReflexCalculator();
        }
        return reflexCalculator;
    }


}