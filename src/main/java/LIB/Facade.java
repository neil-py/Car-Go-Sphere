/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LIB;

/**
 *
 * @author neil
 */
public class Facade implements IFacade{
    private IDBConnect car;
    private IDBConnect customer;
    private IDBConnect bookrental;
    
    public Facade(IDBConnect x){
        if (x instanceof Car){
            this.car=x;
        } else if(x instanceof Customer){
            this.customer=x;
        } else {
            this.bookrental=x;
        }
    }
    
    @Override
    public void registerCar(){
        car.register();
    }
    
    @Override
    public void registerCustomer(){
        customer.register();
    }
    
    @Override
    public void registerRental(){
        bookrental.register();
    }
}
