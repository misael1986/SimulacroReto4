/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edu.unbosque.simulacroreto4;

import java.util.ArrayList;

/**
 *
 * @author Misael Fdo Perilla - UNBOSQUE
 * @version 1.0.1
 * @since 2021
 * @see ArrayList
 */
public class Controlador {

    Vista v;//Parametro para utilizar la vista y capturar/enviar datos al usuario
    ServiciosPersona m;

    Controlador(Vista vis, ServiciosPersona mod) {
        this.v = vis;
        this.m = mod;

        while (this.v.Menu() != 6) {//WHILE hace que le menu sea ciclico, 
            //Menu retorna los enteros capturados en la Vista
            this.Opciones();//invocar el metodo que conecta la vista-controlador
        }
        this.v.Mensajes("Hasta pronto");

    }

    public void Opciones() {
        switch (this.v.getOpcion()) {//Valida la entrada del entero en el Menu
            case 1://Ingresar Persona
                this.v.PedirDatos();
                int msj = this.m.LlenarLista(this.v.getNomRemp(),
                        this.v.getApeRemp(),
                        this.v.getEdadRem(),
                        this.v.getEmailRemp(),
                        this.v.getCarreraRemp());
                if (msj == 1) {
                    this.v.Mensajes("Se agregó la persona");
                } else {
                    this.v.Mensajes("Hubo un problema, NO se agregó la persona");
                }
                break;
            case 2://Menu de Consultas
                Menu_Consultas();
                break;

            case 3://Este es para invocar a Modificar Persona
                this.v.buscarApellido();
                String b = this.v.getApeRemp();
                this.v.PedirDatos();
                this.m.modificarPersona(b, this.v.getNomRemp(),
                        this.v.getApeRemp(),
                        this.v.getEdadRem(),
                        this.v.getEmailRemp(),
                        this.v.getCarreraRemp());
                this.v.Mensajes("Se modificó la persona");
                break;
            case 4://Eliminar Persona
                this.v.buscarApellido();
                this.m.borrarPersona(this.v.getApeRemp());
                this.v.Mensajes("Se eliminó la persona");
                break;
            case 5://Ver directorio de Persona
                
                this.v.ImprimirVarios(this.m.buscarTodos());
                break;

            default:
                this.v.Mensajes("Hasta pronto");
                break;

        }
    }

    public void Menu_Consultas() {
        this.v.MenuConsultas();
        int c = this.v.getConsultas();
        switch (c) {
            case 1:
                this.v.buscarEmail();
                this.v.ImprimirVarios(
                        this.m.buscarPorCorreo(
                                this.v.getEmailRemp()
                        )
                );
                break;
            case 2:
                this.v.buscarApellido();
                this.v.ImprimirVarios(
                        this.m.buscarParticular(
                                this.v.getApeRemp()
                        )
                );

                break;
            case 3:
                this.v.buscarCarrera();
                this.v.ImprimirAlumnosCarrera(
                        this.m.ListarPorCarrera(
                                this.v.getCarreraRemp()
                        )
                );

                break;

            case 4:
                this.v.buscarCarrera();
                this.v.Mensajes("Cantidad de estudiantes "
                        + this.v.getCarreraRemp()
                        + " "
                        + this.m.CantCarreras(this.v.getCarreraRemp()));
                break;
            case 5:
                this.v.buscarEdad();
                this.v.ImprimirVarios(
                        this.m.ListarPorEdad(
                                this.v.getEdadRem()
                        )
                );
                break;
            case 6:
                this.v.buscarApellido();
                this.v.ImprimirAlumnosApellidos(
                        this.m.buscarParticular(
                                this.v.getApeRemp()
                        )
                );
                break;
            default:
                break;

        }

    }

}
