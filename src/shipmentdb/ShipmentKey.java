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

public class ShipmentKey implements Serializable
{
    private String partNumber;
    private String supplierNumber;


    public ShipmentKey(String partNumber, String supplierNumber)
    {
        this.partNumber = partNumber;
        this.supplierNumber = supplierNumber;
    }


    public final String getPartNumber()
    {
        return partNumber;
    }


    public final String getSupplierNumber()
    {
        return supplierNumber;
    }


    public String toString()
    {
        return "[ShipmentKey: supplier=" + supplierNumber +
                " part=" + partNumber + ']';
    }
}