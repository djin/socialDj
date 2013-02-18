package elementosInterfaz;

import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.event.MouseInputListener;

/**
 *
 * @author 66785320
 */
public class MyMouseListener implements MouseInputListener {

    private JButton btn;
    private String botonRatonEncima, botonSinRatonEncima;

    public MyMouseListener(JButton boton, String botonSinRaton, String botonConRaton) {
        btn = boton;
        botonRatonEncima = botonConRaton;
        botonSinRatonEncima = botonSinRaton;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        btn.setIcon(new ImageIcon(botonRatonEncima));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        btn.setIcon(new ImageIcon(botonSinRatonEncima));
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}
