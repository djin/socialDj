/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.datos;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author 66785270
 */
public class ListaPromocionada {

    private ArrayList<CancionPromocionada> canciones;

    public ListaPromocionada() {
        canciones = new ArrayList();
    }

    public ListaPromocionada(ListaCanciones lista_promocionada) {
        canciones = new ArrayList();
        for (Cancion c : lista_promocionada.getCanciones()) {
            canciones.add(new CancionPromocionada(c.id, c.nombreCancion, c.nombreAlbum, c.album_id, c.nombreAutor, c.duracion, c.path));
        }
    }

    /**
     * @return the canciones
     */
    public ArrayList<CancionPromocionada> getCanciones() {
        return canciones;
    }

    public void removeCanciones(ArrayList<CancionPromocionada> canciones) {
        this.canciones.removeAll(canciones);
    }

    public CancionPromocionada getMaxVoto() {
        int i = 0;
        ArrayList<CancionPromocionada> maxCancion = new ArrayList<CancionPromocionada>();
        for (CancionPromocionada c : canciones) {
            if (i <= c.getVotos()) {
                i = c.getVotos();
            }
        }
        for(CancionPromocionada c : canciones){
            if(i == c.getVotos()){
                maxCancion.add(c);
            }
        }
        if(maxCancion.size() > 1){
            Random r = new Random();
            i = r.nextInt(maxCancion.size());
        } else {
            i = 0;
        }
        return maxCancion.get(i);
    }
}