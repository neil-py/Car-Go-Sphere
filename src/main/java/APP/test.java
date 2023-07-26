/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package APP;

import LIB.Car;
import LIB.Facade;
import LIB.IFacade;

/**
 *
 * @author neil
 */
public class test {
    public static void main (String[] args){
        IFacade create = new Facade(new Car("Toyota", "Camry", "Sedan", "ABC123", 2022, "Petrol", 2000.00, "Available"));
        create.registerCar();
        
        
    }
}
