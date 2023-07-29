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
    private Backend car;
    private Backend customer;
    private Backend bookrental;
    
    public Facade(Backend x){
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
