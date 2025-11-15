package vista;

import Modelo.Cliente;
import Modelo.Conexion;
import Persistencia.ClienteData;
import java.util.List;

public class TestConsola {

    public static void main(String[] args) {

        //Probamos conexion
        Conexion.getConexion();

        //Crear instancia de ClienteData
        ClienteData cd = new ClienteData();

        //Crear un cliente nuevo
        Cliente cli = new Cliente(
                "43839685",
                "Ortiz Lourdes",
                "2657642111",
                23,
                "sin afecciones",
                true
        );

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
