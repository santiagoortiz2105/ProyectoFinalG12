/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package vista;

import Modelo.Conexion;
import Modelo.Entidad;
import Persistencia.EntidadData;
import java.util.List;

/**
 *
 * @author Lulim
 */
public class TestConsola {

    /**
     * @param args the command line arguments
     */ 
    public static void main(String[] args) {
        //Probamos conexion
        Conexion.getConexion();

        EntidadData data = new EntidadData();
        Entidad e1 = new Entidad(0, "Masaje Relajante", "Tratamiento corporal básico", true);

        //Guardar nueva entidad
        data.guardarEntidad(e1);

        //Lista de entidades que ya existan
        List<Entidad> lista = data.listarEntidades();
        for (Entidad e : lista) {
            System.out.println(e);
        }

        //Que cierre la conexion
        Conexion.cerrarConexion();
    }
    }
   
