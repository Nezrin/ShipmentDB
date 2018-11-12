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
import com.sleepycat.db.Db;
import com.sleepycat.db.DbException;
import com.sleepycat.db.DbEnv;
import java.io.FileNotFoundException;
import java.io.IOException;
import com.sleepycat.bdb.StoredClassCatalog;
import com.sleepycat.bdb.bind.serial.SerialFormat;
import com.sleepycat.bdb.DataStore;

public class SampleDatabase {

    private DbEnv env;
    private static final String CLASS_CATALOG = "java_class_catalog"; // is a database store
    private StoredClassCatalog javaCatalog; //it contains metadata for other stores and is therefore treated specially by the Java API. 
    //////////////////The SampleDatabase class is extended to open and close the three stores./////////////
    ////////////////////////////////////////////////////////////////////////////////
    private static final String SUPPLIER_STORE = "supplier_store";
    private static final String PART_STORE = "part_store";
    private static final String SHIPMENT_STORE = "shipment_store";

    private DataStore supplierStore;
    private DataStore partStore;
    private DataStore shipmentStore;
    private SerialFormat partKeyFormat;
    private SerialFormat partValueFormat;
    private SerialFormat supplierKeyFormat;
    private SerialFormat supplierValueFormat;
    private SerialFormat shipmentKeyFormat;
    private SerialFormat shipmentValueFormat;
    ////////////////////////////////////////////////////////////////////////////////

    public SampleDatabase(String homeDirectory, boolean runRecovery)
            throws DbException, FileNotFoundException {
        int envFlags = Db.DB_INIT_TXN | Db.DB_INIT_LOCK | Db.DB_INIT_MPOOL
                | Db.DB_CREATE;
        if (runRecovery) {
            envFlags |= Db.DB_RECOVER;
        }
        env = new DbEnv(0);
        System.out.println("Opening environment in: " + homeDirectory);
        env.open(homeDirectory, envFlags, 0);

        int flags = Db.DB_CREATE | Db.DB_AUTO_COMMIT;
        javaCatalog = new StoredClassCatalog(env, CLASS_CATALOG, null, flags);//open the class catalog
        
        ///////////////////////////////create the format objects.///////////////////
        partKeyFormat = new SerialFormat(javaCatalog, PartKey.class);
        partValueFormat = new SerialFormat(javaCatalog, PartValue.class);
        supplierKeyFormat = new SerialFormat(javaCatalog, SupplierKey.class);
        supplierValueFormat = new SerialFormat(javaCatalog, SupplierValue.class);
        shipmentKeyFormat = new SerialFormat(javaCatalog, ShipmentKey.class);
        shipmentValueFormat = new SerialFormat(javaCatalog, ShipmentValue.class);
        /////////////////////////////////////////////////////////////////////////////
        
        /////////////////open the three stores using the Db and DataStore classes. ///////////////////

        Db partDb = new Db(env, 0); 
        partDb.open(null, PART_STORE, null, Db.DB_BTREE, flags, 0);
        partStore = new DataStore(partDb, partKeyFormat, partValueFormat, null);

        Db supplierDb = new Db(env, 0);
        supplierDb.open(null, SUPPLIER_STORE, null, Db.DB_BTREE, flags, 0);
        supplierStore = new DataStore(supplierDb, supplierKeyFormat, supplierValueFormat, null);

        Db shipmentDb = new Db(env, 0);
        shipmentDb.open(null, SHIPMENT_STORE, null, Db.DB_BTREE, flags, 0);
        shipmentStore = new DataStore(shipmentDb, shipmentKeyFormat, shipmentValueFormat, null);

        javaCatalog = new StoredClassCatalog(env, CLASS_CATALOG, null, flags);
        env = new DbEnv(0);
        System.out.println("Opening environment in: " + homeDirectory);
        env.open(homeDirectory, envFlags, 0);

    }

    public void close()
            throws DbException, IOException {
        partStore.close();
        supplierStore.close();
        shipmentStore.close();
        javaCatalog.close();
        env.close(0);
    }

    public final DbEnv getEnvironment() {
        return env;
    }

    public final SerialFormat getPartKeyFormat() {
        return partKeyFormat;
    }

    public final SerialFormat getPartValueFormat() {
        return partValueFormat;
    }

    public final SerialFormat getSupplierKeyFormat() {
        return supplierKeyFormat;
    }

    public final SerialFormat getSupplierValueFormat() {
        return supplierValueFormat;
    }

    public final SerialFormat getShipmentKeyFormat() {
        return shipmentKeyFormat;
    }

    public final SerialFormat getShipmentValueFormat() {
        return shipmentValueFormat;
    }

    public final DataStore getPartStore() {
        return partStore;
    }

    public final DataStore getSupplierStore() {
        return supplierStore;
    }

    public final DataStore getShipmentStore() {
        return shipmentStore;
    }

}
