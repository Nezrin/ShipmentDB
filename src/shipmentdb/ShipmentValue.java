/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shipmentdb;

/**
 *
 * @author nazrin
 */
import java.io.Serializable;

public class ShipmentValue implements Serializable
{
    private int quantity;


    public ShipmentValue(int quantity)
    {
        this.quantity = quantity;
    }


    public final int getQuantity()
    {
        return quantity;
    }


    public String toString()
    {
        return "[ShipmentValue: quantity=" + quantity + ']';
    }
}


