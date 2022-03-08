/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edu.unbosque.simulacroreto4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 *
 */
public class ServiciosPersona {

    ArrayList<Persona> lista;//TODO queda acá guardado (BD Imaginaria)

    ServiciosPersona() {
        /*
        this.lista = new ArrayList<Persona>();//
         */

        /*
        this.lista = this.Leer();
         */
        if (buscarTodos() == null) {
            //System.out.println("Se reinicia la carga del CVS a la BD");
            PrimeraVez();
        }

    }

    //----CREAR LA CONEXIÓN A LA BASE DE DATOS--------
    private Connection ConectarBD() {
        // SQLite connection string
        String url = "jdbc:sqlite:bd_estudiantes.db";//RUTA DINAMICA

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("Error al Conectar: " + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }

    //------------SELECCIONA a TODOS DE LA BD---------------------------------------
    public ArrayList<Persona> buscarTodos() {//retorna la lista entera
        ArrayList<Persona> BD = null;
        String sql = "SELECT * FROM estudiantes";
        //             el famoso SELECT * from tabla

        try (Connection conn = this.ConectarBD();
                Statement stmt = conn.createStatement();//Preparar Consulta -> Abrir la tabla
                ResultSet rs = stmt.executeQuery(sql)) {//ResultSet carga almacena
            //El resulatdo de la consulta
            BD = new ArrayList();
            Persona temporal;
            while (rs.next()) {
                temporal = new Persona(
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getLong("edad"),
                        rs.getString("email"),
                        rs.getString("carrera")
                );
                BD.add(temporal);
            }
            conn.close();
        } catch (SQLException e) {

            System.out.println("Error al tratar de Leer Todo: " + e.getMessage());
            e.printStackTrace();
        }
        return BD;
    }

    //------------CREEAR UNA PERSONA Y AGREGARLA A BD---------------------------------------
    public int LlenarLista(String nom, String ape, long edad, String mail,
            String carrera) {
        int exito = 0;//bandera de si el proceso de guardado fue exitoso 

        String sql = "INSERT INTO estudiantes(nombre,apellido,edad,email,carrera) "
                + "VALUES(?,?,?,?,?)";//OJO con la CANTIDAD
        // Cada ? es un Parametro a ser modificado despues

        try (Connection conn = this.ConectarBD();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nom);//Acá se sustituye cada ? por el valor
            pstmt.setString(2, ape);
            pstmt.setLong(3, edad);
            pstmt.setString(4, mail);
            pstmt.setString(5, carrera);
            //System.out.println("Consulta de guardado es: " + pstmt.toString());

            pstmt.executeUpdate();//Ejecuta el comando SQL con los parámetros
            //modificados - executeUpdate se usa cuando
            //NO SE ESPERA TRAER DATOS DESDE LA BD
            conn.close();
            exito = 1;
        } catch (SQLException e) {
            System.out.println("Problema al guardar en la BD: ");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return exito;

    }

    //------------BUSCAR A ALGUIEN ESPECIFICO---------------------------------------
    public ArrayList<Persona> buscarParticular(String ape) {
        Persona res = null;
        ArrayList<Persona> variosPer = null;
        String sql = "SELECT * FROM estudiantes WHERE apellido = '" + ape + "'";
        //Consulta 'quemada'

        try (Connection conn = this.ConectarBD();
                Statement pstmt = conn.createStatement()) {

            //System.out.println("Consulta de busqueda email: "+pstmt.toString());
            ResultSet rs = pstmt.executeQuery(sql);//executeQuery se usa cuando
            //SI SE ESPERA TRAER DATOS DESDE LA BD

            variosPer = new ArrayList();
            while (rs.next()) {
                res = new Persona(rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getLong("edad"),
                        rs.getString("email"),
                        rs.getString("carrera")
                );
                variosPer.add(res);
            }

            conn.close();
        } catch (SQLException e) {
            System.out.println("Error al Buscar: " + e.getMessage());
            e.printStackTrace();
        }

        return variosPer;
    }

    //---------------BUSCAR POR EMAIL--------------------------
    public ArrayList<Persona> buscarPorCorreo(String bmail) {
        Persona res = null;
        ArrayList<Persona> Varios = null;
        String sql = "SELECT * FROM estudiantes WHERE email = '" + bmail + "'";
        //Consulta 'quemada'

        try (Connection conn = this.ConectarBD();
                Statement pstmt = conn.createStatement()) {
            //pstmt.setString(1, bmail);
            //System.out.println("Consulta de busqueda email: "+sql);
            ResultSet rs = pstmt.executeQuery(sql);
            Varios = new ArrayList();
            while (rs.next()) {
                res = new Persona(rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getLong("edad"),
                        rs.getString("email"),
                        rs.getString("carrera")
                );
                Varios.add(res);
            }

            conn.close();
        } catch (SQLException e) {
            System.out.println("Error al Buscar: " + e.getMessage());
            e.printStackTrace();
        }
        return Varios;
    }

    //----------------BUSCA LA CATIDAD DE ALUMNOS EN UN PROGRAMA---------------
    public int CantCarreras(String carr) {
        int cantidad = 0;
        String sql = "Select count(nombre) as cantidad from estudiantes "
                + "where carrera='" + carr + "'";
        // count cuenta cuantos resultados llegar de la consulta

        try (Connection conn = this.ConectarBD();
                Statement stmt = conn.createStatement();//Preparar Consulta -> Abrir la tabla
                ResultSet rs = stmt.executeQuery(sql)) {//ResultSet carga almacena
            //El resulatdo de la consulta

            while (rs.next()) {
                cantidad = rs.getInt("cantidad");
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error al tratar de Leer Todo: " + e.getMessage());
            e.printStackTrace();
        }
        return cantidad;
    }

    //------------BUSCAR PERSONAS PRO CARRERA---------------------------------------
    public ArrayList<Persona> ListarPorCarrera(String carr) {
        Persona res = null;
        ArrayList<Persona> variosPer = null;
        String sql = "SELECT nombre,apellido FROM estudiantes WHERE "
                + "carrera = '" + carr + "'";
        //Consulta 'quemada'

        try (Connection conn = this.ConectarBD();
                Statement pstmt = conn.createStatement()) {

            //System.out.println("Consulta de busqueda email: "+pstmt.toString());
            ResultSet rs = pstmt.executeQuery(sql);//executeQuery se usa cuando
            //SI SE ESPERA TRAER DATOS DESDE LA BD

            variosPer = new ArrayList();
            while (rs.next()) {
                res = new Persona(rs.getString("nombre"),
                        rs.getString("apellido")
                );
                variosPer.add(res);
            }

            conn.close();
        } catch (SQLException e) {
            System.out.println("Error al Buscar: " + e.getMessage());
            e.printStackTrace();
        }

        return variosPer;
    }

    //------------------------BUSCA POR EDAD----------------------------
    public ArrayList<Persona> ListarPorEdad(long eda) {
        Persona res = null;
        ArrayList<Persona> variosPer = null;
        String sql = "SELECT * FROM estudiantes WHERE edad = " + eda + "";
        //Consulta 'preparada'

        try (Connection conn = this.ConectarBD();
                Statement pstmt = conn.createStatement()) {

            //System.out.println("Consulta de busqueda email: "+pstmt.toString());
            ResultSet rs = pstmt.executeQuery(sql);//executeQuery se usa cuando
            //SI SE ESPERA TRAER DATOS DESDE LA BD

            variosPer = new ArrayList();
            while (rs.next()) {
                res = new Persona(rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getLong("edad"),
                        rs.getString("email"),
                        rs.getString("carrera")
                );
                variosPer.add(res);
            }

            conn.close();
        } catch (SQLException e) {
            System.out.println("Error al Buscar: " + e.getMessage());
            e.printStackTrace();
        }
        return variosPer;
    }

    //------------MODIFICA A PERSONA---------------------------------------
    public void modificarPersona(String OldApe, String nombre, String Newapell,
            long eda, String mail, String carre) {
        String sql = "UPDATE estudiantes SET nombre = ? , "
                + "apellido = ? ,"
                + "edad = ? ,"
                + "email = ? ,"
                + "carrera = ? "
                + "WHERE apellido = ?";//Lo normal es usar el ID

        try (Connection conn = this.ConectarBD();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            pstmt.setString(2, Newapell);
            pstmt.setLong(3, eda);
            pstmt.setString(4, mail);
            pstmt.setString(5, carre);
            pstmt.setString(6, OldApe);

            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error al Modificar: " + e.getMessage());
            e.printStackTrace();
        }

    }

    //------------BORRAR A PERSONA---------------------------------------
    public void borrarPersona(String ape) {
        String sql = "DELETE FROM estudiantes WHERE apellido = ?";

        try (Connection conn = this.ConectarBD();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ape);
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error al Borrar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //---------------CREAR LA TABLA SI SE NECESITA---------------------
    public void PrimeraVez() {
        crearTablaBD();//CASO EXTREMO PERO POSIBLE
        String csvFile = "infoestudiantes.csv";
        BufferedReader br = null;
        String line = "";

        String cvsSplitBy = ",";//Se define separador ","
        try {
            br = new BufferedReader(new FileReader(csvFile));//se lee el archivo
            while ((line = br.readLine()) != null) {//se recorre linea * linea
                String[] datos = line.split(cvsSplitBy);//se separa usando la coma ','
                /*System.out.println(datos[0]+"\t"+
                        datos[1]+"\t"+
                        Long.parseLong(datos[2])+"\t"+
                        datos[3]+"\t"+
                        datos[4]);
                */
                LlenarLista(datos[0],
                        datos[1],
                        Long.parseLong(datos[2]),//covierte el String a Long
                        datos[3],
                        datos[4]);
            }
            //System.out.println("Carga de CSV a BD Finalizada");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void crearTablaBD() {
        String sql = " CREATE TABLE IF NOT EXISTS 'estudiantes' ("
                + "'idEstudiante'	INTEGER NOT NULL,"
                + "'nombre'	TEXT NOT NULL,"
                + "'apellido'	TEXT NOT NULL,"
                + "'email'	TEXT NOT NULL UNIQUE,"
                + "'edad'	INTEGER,"
                + "'carrera' TEXT,"
                + "PRIMARY KEY('idEstudiante' AUTOINCREMENT)"
                + ") ";
        //Consulta 'preparada' para crear la Tabla si NO EXISTE

        try (Connection conn = this.ConectarBD();
                Statement pstmt = conn.createStatement()) {

            pstmt.executeUpdate(sql);//executeUpdate se usa cuando
            //NO SE ESPERA TRAER DATOS DESDE LA BD
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error al Crear la Tabla: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
