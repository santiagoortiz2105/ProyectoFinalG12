package vista;

import Modelo.Cliente;
import Modelo.Conexion;
import Persistencia.ClienteData;
import java.util.List;

public class TestConsola {

    public static void main(String[] args) {
         java.awt.EventQueue.invokeLater(() -> {
            new frmMenuPrincipal().setVisible(true);
        });
    }
}
