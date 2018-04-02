/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import domain.Vehicle;
import file.VehicleFile;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Steven
 */
public class FileTest {
    
    public FileTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void vehicleTest() {
        System.out.println("Probando crear un nuevo vehiculo");
        Vehicle vehicleTest = new Vehicle("Toyota", 2018, 0, false, 123456);
        System.out.println(vehicleTest);
    }
    
    @Test
    public void fileTestAddEndAndGetAll() throws IOException{
        System.out.println("Probando agregar nuevo y obtener todos");
        File file = new File("TestVehicle.dat");
        VehicleFile vf= new VehicleFile(file);
        Vehicle vehicleTest2 = new Vehicle("Honda", 2015, 0, false, 33225577);
        
        vf.addEndRecord(vehicleTest2);
        
        ArrayList<Vehicle> av=vf.getAllVehicles();
        
        for(int i=0; i<av.size(); i++){
            System.out.println(av.get(i).toString());
        }
    }
    
    @Test
    public void fileTestDeleteAndUpdate() throws IOException{
        System.out.println("Probando eliminar y actualizar vehiculo.");
        File file = new File("TestVehicle.dat");
        VehicleFile vf= new VehicleFile(file);
        Vehicle vt1 = new Vehicle("Nissan", 2015, 0, false, 5555);
        Vehicle vt2 = new Vehicle("BMW", 2018, 0, true, 77777);
        Vehicle vt3 = new Vehicle("Audi", 2017, 0, true, 333333);
        
        vf.addEndRecord(vt1);
        vf.addEndRecord(vt2);
        vf.addEndRecord(vt3);
        
        vf.delete(77777);
        vf.update(5555, "GMC", 2010, true);
    }
}
