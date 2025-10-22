/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package vista;

import Modelo.Cliente;
import Modelo.Conexion;
import Persistencia.ClienteData;
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
        
         //Crear instancia de ClienteData
        ClienteData cd = new ClienteData();

        //Crear un cliente nuevo
        Cliente cli = new Cliente();
        
        
        //Guardar clientes en la base de datos 
        cd.guardarCliente(cli);

        // Lista de los clientes ya existentes
        System.out.println("Lista de clientes:");
        List<Cliente> lista = cd.listarClientes();
        for (Cliente c : lista) {
            System.out.println(c);
        }

        //Cerrar Conexion 
        Conexion.cerrarConexion();
    }
    }
   
