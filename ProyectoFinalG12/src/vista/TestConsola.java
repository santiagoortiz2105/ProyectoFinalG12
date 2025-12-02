package vista;

public class TestConsola {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new frmMenuPrincipal().setVisible(true);
        });
    }
}
