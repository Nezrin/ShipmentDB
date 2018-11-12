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

public class SupplierKey implements Serializable
{
    private String number;


    public SupplierKey(String number)
    {
        this.number = number;
    }


    public final String getNumber()
    {
        return number;
    }


    public String toString()
    {
        return "[SupplierKey: number=" + number + ']';
    }
}

