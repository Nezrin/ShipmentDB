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
import com.sleepycat.bdb.TransactionRunner;
import com.sleepycat.bdb.TransactionWorker;
import com.sleepycat.bdb.collection.StoredIterator;
import com.sleepycat.db.DbException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class Sample {

    private SampleDatabase db;
    private SampleViews views;

    public static void main(String[] args) {
        System.out.println("\nRunning sample: " + Sample.class);
        boolean runRecovery = true;
        String homeDir = "./tmp";
        for (int i = 0; i < args.length; i += 1) {
            String arg = args[i];
            if (args[i].equals("-h") && i < args.length - 1) {
                i += 1;
                homeDir = args[i];
            } else if (args[i].equals("-multiprocess")) {
                runRecovery = false;
            } else {
                System.err.println("Usage:\n java " + Sample.class.getName()
                        + "\n  [-h <home-directory>] [-multiprocess]");
                System.exit(2);
            }
        }
        Sample sample = null;
        try {
            sample = new Sample(homeDir, runRecovery); 
            sample.run();
        } catch (Exception e) {
            e.printStackTrace();
        } 
        finally {
            if (sample != null) {
                try {
                    sample.close();
                } catch (Exception e) {
                    System.err.println("Exception during database close:");
                    e.printStackTrace();
                }
            }
        }
    }

    private Sample(String homeDir, boolean runRecovery)
            throws DbException, FileNotFoundException {
        db = new SampleDatabase(homeDir, runRecovery);
        views = new SampleViews(db);
    }

    private void run()
            throws Exception {
        TransactionRunner runner = new TransactionRunner(db.getEnvironment());
        runner.run(new PopulateDatabase());
        runner.run(new PrintDatabase());
    }

    private void close() throws DbException, IOException {
        db.close();
    }

    private class PopulateDatabase implements TransactionWorker {

        public void doWork()
                throws Exception {
            addSuppliers();
            addParts();
            addShipments();
        }        
    }
     private void addSuppliers()
    {
        Map suppliers = views.getSupplierMap();
        if (suppliers.isEmpty())
        {
            System.out.println("Adding Suppliers");
            suppliers.put(new SupplierKey("S1"),
                          new SupplierValue("Smith", 20, "London"));
            suppliers.put(new SupplierKey("S2"),
                          new SupplierValue("Jones", 10, "Paris"));
            suppliers.put(new SupplierKey("S3"),
                          new SupplierValue("Blake", 30, "Paris"));
            suppliers.put(new SupplierKey("S4"),
                          new SupplierValue("Clark", 20, "London"));
            suppliers.put(new SupplierKey("S5"),
                          new SupplierValue("Adams", 30, "Athens"));
        }
    }

    private void addParts()
    {
        Map parts = views.getPartMap();
        if (parts.isEmpty())
        {
            System.out.println("Adding Parts");
            parts.put(new PartKey("P1"),
                      new PartValue("Nut12", "Red",
                                    new Weight(12.0, Weight.GRAMS),
                                    "London"));
            parts.put(new PartKey("P2"),
                      new PartValue("Bolt", "Green",
                                    new Weight(17.0, Weight.GRAMS),
                                    "Paris"));
            parts.put(new PartKey("P3"),
                      new PartValue("Screw", "Blue",
                                    new Weight(17.0, Weight.GRAMS),
                                    "Rome"));
            parts.put(new PartKey("P4"),
                      new PartValue("Screw", "Red",
                                    new Weight(14.0, Weight.GRAMS),
                                    "London"));
            parts.put(new PartKey("P5"),
                      new PartValue("Cam", "Blue",
                                    new Weight(12.0, Weight.GRAMS),
                                    "Paris"));
            parts.put(new PartKey("P6"),
                      new PartValue("Cog", "Red",
                                    new Weight(19.0, Weight.GRAMS),
                                    "London"));
        }
    }


    private void addShipments()
    {
        Map shipments = views.getShipmentMap();
        if (shipments.isEmpty())
        {
            System.out.println("Adding Shipments");
            shipments.put(new ShipmentKey("P1", "S1"),
                          new ShipmentValue(300));
            shipments.put(new ShipmentKey("P2", "S1"),
                          new ShipmentValue(200));
            shipments.put(new ShipmentKey("P3", "S1"),
                          new ShipmentValue(400));
            shipments.put(new ShipmentKey("P4", "S1"),
                          new ShipmentValue(200));
            shipments.put(new ShipmentKey("P5", "S1"),
                          new ShipmentValue(100));
            shipments.put(new ShipmentKey("P6", "S1"),
                          new ShipmentValue(100));
            shipments.put(new ShipmentKey("P1", "S2"),
                          new ShipmentValue(300));
            shipments.put(new ShipmentKey("P2", "S2"),
                          new ShipmentValue(400));
            shipments.put(new ShipmentKey("P2", "S3"),
                          new ShipmentValue(200));
            shipments.put(new ShipmentKey("P2", "S4"),
                          new ShipmentValue(200));
            shipments.put(new ShipmentKey("P4", "S4"),
                          new ShipmentValue(300));
            shipments.put(new ShipmentKey("P5", "S4"),
                          new ShipmentValue(400));
        }
    }
     private class PrintDatabase implements TransactionWorker
    {
        public void doWork()
            throws Exception
        {
            printEntries("Parts",
                          views.getPartEntrySet().iterator());
            printEntries("Suppliers",
                          views.getSupplierEntrySet().iterator());
            printEntries("Shipments",
                          views.getShipmentEntrySet().iterator());
        }
    }

    private void printEntries(String label, Iterator iterator)
    {
        System.out.println("\n--- " + label + " ---");
        try
        {
            while (iterator.hasNext())
            {
                Map.Entry entry = (Map.Entry) iterator.next();
                System.out.println(entry.getKey().toString());
                System.out.println(entry.getValue().toString());
            }
        }
        finally
        {
            StoredIterator.close(iterator);
        }
    }
}
