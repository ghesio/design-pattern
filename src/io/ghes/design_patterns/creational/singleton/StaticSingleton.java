package io.ghes.design_patterns.creational.singleton;

public class StaticSingleton {

    private static StaticSingleton instance;
    
    //private constructor to avoid client applications to use constructor
    private StaticSingleton(){}
    
    //static block initialization for exception handling
    static{
        try{
            instance = new StaticSingleton();
        }catch(Exception e){
            throw new RuntimeException("Exception occured in creating singleton instance");
        }
    }
    
    public static StaticSingleton getInstance(){
        return instance;
    }
}