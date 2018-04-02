/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 * @author Steven
 * @author Eyleen
 */
public class Vehicle {
   //Atributos
    private String brand;
    private int year;
    private float mileage;
    private boolean american;
    private int serial;
    
    //constructores
    public Vehicle(){
        this.brand="";
        this.year=0;
        this.mileage=0;
        this.american=false;
        this.serial=0;
    }

    public Vehicle(String brand, int year, float mileage, boolean american, int serial) {
        this.brand = brand;
        this.year = year;
        this.mileage = mileage;
        this.american = american;
        this.serial = serial;
    }

    //Accesores
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getMileage() {
        return mileage;
    }

    public void setMileage(float mileage) {
        this.mileage = mileage;
    }

    public boolean isAmerican() {
        return american;
    }

    public void setAmerican(boolean american) {
        this.american = american;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    //toString
    @Override
    public String toString() {
        return "Marca=" + brand + ", Año=" + year + ", Kilometraje=" + mileage + ", Americano=" + american + ", Serie=" + serial;
    }
    
    //Tamaño en bytes de las variables
    public int sizeInBytes(){
        return (this.getBrand().length() * 2) + 4 + 4 + 1 + 4;
    } 
}
