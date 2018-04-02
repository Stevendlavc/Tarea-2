/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file;

import domain.Vehicle;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 * @author Steven
 * @author Eyleen
 */
public class VehicleFile {

    //Atributos
    public RandomAccessFile randomAccessFile;
    private int regsQuantity;
    private int regSize;

    //Constructores
    public VehicleFile(File file) throws IOException {
        //Tamaño de los registros
        this.regSize = 33;

        //Validacion sobre la existencia del archivo
        if (file.exists() && !file.isFile()) {
            throw new IOException(file.getName() + " is an invalid file");
        } else {
            //Instancia de Random Access File
            this.randomAccessFile = new RandomAccessFile(file, "rw");

            //Cantidad de registros
            this.regsQuantity = (int) Math.ceil((double) this.randomAccessFile.length() / (double) this.regSize);
        }
    }//End method

    //Método de cerrado del archivo
    public void close() throws IOException {
        this.randomAccessFile.close();
    }

    //Método de inserción de datos al archivo
    public boolean putValue(int spot, Vehicle newVehicle) throws IOException {
        if (spot < 0 && spot > this.regsQuantity) {
            System.err.println("F1 - Record position is out of bounds");
            return false;
        } else {
            if (newVehicle.sizeInBytes() > this.regSize) {
                System.err.println("F2 - Record size is out of bounds");
                return false;
            } else {
                randomAccessFile.seek(spot * this.regSize);
                randomAccessFile.writeUTF(newVehicle.getBrand());
                randomAccessFile.writeInt(newVehicle.getYear());
                randomAccessFile.writeFloat(newVehicle.getMileage());
                randomAccessFile.writeBoolean(newVehicle.isAmerican());
                randomAccessFile.writeInt(newVehicle.getSerial());
                return true;
            }
        }

    }//End method

    //Insertar al final del archivo
    public boolean addEndRecord(Vehicle newVehicle) throws IOException {

        boolean success = putValue(this.regsQuantity, newVehicle);//Pone el brazo en la última posición para agregar otro vehículo
        if (success) {
            this.regsQuantity++;//Se aumenta la cantidad de registros
        }
        return success;
    }//End method

    //Obtener un vehículo
    public Vehicle getVehicle(int position) throws IOException {
        if (position >= 0 && position <= this.regsQuantity) {
            //Colocamos el brazo en el lugar adecuado
            this.randomAccessFile.seek(position * this.regSize);

            //Llevamos a cabo la lectura
            Vehicle vehicleTemp = new Vehicle();
            vehicleTemp.setBrand(randomAccessFile.readUTF());
            vehicleTemp.setYear(randomAccessFile.readInt());
            vehicleTemp.setMileage(randomAccessFile.readFloat());
            vehicleTemp.setAmerican(randomAccessFile.readBoolean());
            vehicleTemp.setSerial(randomAccessFile.readInt());

            if (vehicleTemp.getSerial() == 0) {
                return null;
            } else {
                return vehicleTemp;
            }
        } else {
            System.err.println("F3- the brand ...");
            return null;
        }
    }//end method

    //Eliminar un vehículo
    public boolean delete(int serial) throws IOException {
        Vehicle vehicle;
        this.regsQuantity = (int) Math.ceil((double) randomAccessFile.length() / (double) regSize);
        //Ciclo buscar el vehículo
        for (int i = 0; i < this.regsQuantity; i++) {
            //obtener el vehículo de la posicion actual
            vehicle = this.getVehicle(i);

            //Pregunta si es igual al vehículo que quiero borrar
            if (vehicle.getSerial() == serial) {
                //Marcar como eliminado
                vehicle.setBrand("Deleted");
                vehicle.setAmerican(false);
                vehicle.setMileage(-1);
                vehicle.setYear(-1);
                vehicle.setSerial(-1);
                return this.putValue(i, vehicle);
            } else {
            }
        }
        return false;
    }//End method

    //Actualizar la información de un vehículo
    public boolean update(int serial, String brand, int year, boolean american) throws IOException {
        Vehicle vehicle;
        //Ciclo buscar el vehículo
        for (int i = 0; i < this.regsQuantity; i++) {
            //obtener el vehícle de la posicion actual
            vehicle = this.getVehicle(i);

            //Verifica que sea un año válido
            if (year < 1000 || serial <= 0) {
                return false;
            }
            //Pregunta si es igual al vehículo que quiero borrar
            if (vehicle.getSerial() == serial) {
                vehicle.setBrand(brand);
                vehicle.setAmerican(american);
                vehicle.setYear(year);

                return this.putValue(i, vehicle);

            } else {
                //return false;
            }
        }
        return false;
    }//End method

    //Muestra la lista de vehículos registrados
    public ArrayList<Vehicle> getAllVehicles() throws IOException {
        ArrayList<Vehicle> vehicleArray = new ArrayList<Vehicle>();
        //Se vuelve a definir la cantidad de registros
        this.regsQuantity = (int) Math.ceil((double) randomAccessFile.length() / (double) regSize);

        //For que agrega la información de los vehículos al ArrayList
        for (int position = 0; position < this.regsQuantity;) {
            randomAccessFile.seek(position * this.regSize);
            Vehicle temp = new Vehicle();
            temp.setBrand(randomAccessFile.readUTF());
            temp.setYear(randomAccessFile.readInt());
            temp.setMileage(randomAccessFile.readFloat());
            temp.setAmerican(randomAccessFile.readBoolean());
            temp.setSerial(randomAccessFile.readInt());

            if (!temp.getBrand().equals("Deleted")) {
                vehicleArray.add(temp);
            }
            position++;
        }//End for

        return vehicleArray;
    }//End method

    //Verificar serial repetida
    public boolean verifySerial(int serial) throws IOException {
        
        this.regsQuantity = (int) Math.ceil((double) this.randomAccessFile.length() / (double) this.regSize);
        
        Vehicle vehicle;
        //Ciclo buscar el vehículo
        for (int i = 0; i < this.regsQuantity; i++) {
            //obtener el vehículo de la posicion actual
            vehicle = this.getVehicle(i);

            //Pregunta si las series son iguales
            if (vehicle.getSerial() == serial) {
                return true;
            } else {
            }
        }
        return false;
    }//End method

    //Método que comprime el archivo
    public void compress() throws IOException {
        ArrayList<Vehicle> tempList = new ArrayList<Vehicle>();
        int regs = 0;
        
        //Este for lee el archivo para verificar cuales vehículos están eliminados y excluirlos de la lista
        for (int i = 0; i < this.regsQuantity; i++) {
            Vehicle vehicle = new Vehicle();
            randomAccessFile.seek(i * this.regSize);
            vehicle.setBrand(randomAccessFile.readUTF());
            vehicle.setYear(randomAccessFile.readInt());
            vehicle.setMileage(randomAccessFile.readFloat());
            vehicle.setAmerican(randomAccessFile.readBoolean());
            vehicle.setSerial(randomAccessFile.readInt());

            if (!vehicle.getBrand().equals("Deleted")) {
                tempList.add(vehicle);
                regs++;
            }
        }
        
        //Limpia el archivo
        this.randomAccessFile.setLength(0);
        
        Vehicle temp = new Vehicle();
        
        //Este for reescribe el archivo pero sin contar a los eliminados
        for (int i = 0; i < regs; i++) {
            temp = tempList.get(i);
            randomAccessFile.seek(i * this.regSize);
            randomAccessFile.writeUTF(temp.getBrand());
            randomAccessFile.writeInt(temp.getYear());
            randomAccessFile.writeFloat(temp.getMileage());
            randomAccessFile.writeBoolean(temp.isAmerican());
            randomAccessFile.writeInt(temp.getSerial());
        }
        //Con esta línea se calcula nuevamente la cantidad de registros
        this.regsQuantity = (int) Math.ceil((double) this.randomAccessFile.length() / (double) this.regSize);
    }//End method
}
