/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edu.unbosque.simulacroreto4;

import java.io.Serializable;

/**
 *
 * esta es una Clase PLANA, SENCILLA usada para el patrón de diseño DTO Clase
 * ahora es serializable Guarda en Base de datos SQLite3
 *
 * @see Serializable
 * @version 2.0
 * @author Yo
 * @since 2021
 *
 */
public class Persona implements Serializable {

    /*
    * Modificada por Yo persona para cumplir con el cliente MISION TIC 2022
    * de la UNBOSQUE
    *
     */
    private String nombre;
    private String apellidos;
    private long edad;
    private String email;//en la BD debe ser un campo de tipo TEXT, NOT NULL y UNIQUE
    private String carrera;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public long getEdad() {
        return edad;
    }

    /**
     * @param edad sirve para asignar un valor al parametro edad de la clase
     *
     */
    public void setEdad(long edad) {
        this.edad = edad;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String mail) {
        this.email = mail;
    }

    public String getCarrera() {
        return this.carrera;
    }

    public void setCarrera(String carr) {
        this.carrera = carr;
    }

    /**
     * Constructor de la Clase como aparece en DTO
     *
     * @param nombre es para el nombre de la persona (Solo letras)
     * @param apellidos es para los apellidos de la persona (Solo letras)
     * @param edad para la edad de la persona
     */
    
    public Persona(String nombre, String apellidos) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        
    }
    
    public Persona(String nombre, String apellidos, long edad,
            String mail, String carr) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.email = mail;
        this.carrera = carr;
    }
}
