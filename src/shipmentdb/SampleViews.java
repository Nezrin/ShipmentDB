/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shipmentdb;

import com.sleepycat.bdb.bind.DataBinding;
import com.sleepycat.bdb.bind.serial.SerialBinding;
import com.sleepycat.bdb.collection.StoredEntrySet;
import com.sleepycat.bdb.collection.StoredMap;
import java.util.Map;

/**
 *
 * @author nazrin
 */
public class SampleViews {
    private StoredMap partMap;  
    private StoredMap supplierMap;
    private StoredMap shipmentMap;


       public SampleViews(SampleDatabase db)
    {
        
        DataBinding partKeyBinding = new SerialBinding(db.getPartKeyFormat());
        DataBinding partValueBinding = new SerialBinding(db.getPartValueFormat());
        DataBinding supplierKeyBinding = new SerialBinding(db.getSupplierKeyFormat());
        DataBinding supplierValueBinding = new SerialBinding(db.getSupplierValueFormat());
        DataBinding shipmentKeyBinding = new SerialBinding(db.getShipmentKeyFormat());
        DataBinding shipmentValueBinding = new SerialBinding(db.getShipmentValueFormat());
            partMap =
            new StoredMap(db.getPartStore(),
                          partKeyBinding, partValueBinding, true);
        supplierMap =
            new StoredMap(db.getSupplierStore(),
                          supplierKeyBinding, supplierValueBinding, true);
        shipmentMap =
            new StoredMap(db.getShipmentStore(),
                          shipmentKeyBinding, shipmentValueBinding, true);
   
    }

        public final StoredMap getPartMap()
    {
        return partMap;
    }

    public final StoredMap getSupplierMap()
    {
        return supplierMap;
    }


    public final StoredMap getShipmentMap()
    {
        return shipmentMap;
    }


    public final StoredEntrySet getPartEntrySet()
    {
        return (StoredEntrySet) partMap.entrySet();
    }


    public final StoredEntrySet getSupplierEntrySet()
    {
        return (StoredEntrySet) supplierMap.entrySet();
    }


    public final StoredEntrySet getShipmentEntrySet()
    {
        return (StoredEntrySet) shipmentMap.entrySet();
    }
}
