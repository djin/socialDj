package interfaz.social;

import Manejador.ManejadorAcciones;
import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import java.util.ArrayList;

public class ListasCanciones extends FragmentActivity {

    ActionBar.TabListener tabListener;
    // When requested, this adapter returns a DemoObjectFragment,
    // representing an object in the collection.
    SwipeViewPagerAdapter mSwipeViewPagerAdapter;
    ViewPager mViewPager;
    private Menu menuApp;
    private ManejadorAcciones manejador;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        manejador = ManejadorAcciones.getInstance();
        manejador.setListaCanciones(this);
        final ActionBar actionBar = getActionBar();
        // Specify that a dropdown list should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        actionBar.setListNavigationCallbacks(
                // Specify a SpinnerAdapter to populate the dropdown list.
                new ArrayAdapter(actionBar.getThemedContext(), R.layout.style_row_spinner,
                R.id.textSpinner, new String[]{"Listas Preparadas", "Lista en reproduccion"}),
                // Provide a listener to be called when an item is selected.
                new ActionBar.OnNavigationListener() {
            public boolean onNavigationItemSelected(int position, long id) {
                // Take action here, e.g. switching to the
                // corresponding fragment.
                if (position == 1) {
                    actionBar.setSelectedNavigationItem(0);
                    Intent inte = new Intent(ListasCanciones.this, ListaPromocionada.class);
                    inte.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(inte);
                }
                return true;
            }
        });
        actionBar.setDisplayShowTitleEnabled(false);

        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        mSwipeViewPagerAdapter = new SwipeViewPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSwipeViewPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            public void onPageScrolled(int i, float f, int i1) {
                cancelarSeleccion();
            }

            public void onPageSelected(int i) {
                cancelarSeleccion();
            }

            public void onPageScrollStateChanged(int i) {
                cancelarSeleccion();
            }
        });
    }
    
    public void cancelarSeleccion(){
        manejador.cancelarSeleccion();
        desapareceMenuSeleccion();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuApp = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.menulistas, menu);
        menu.getItem(0).getSubMenu().clear();
        menu.add(13, 58, 6, "Promocionar");
        menu.add(12, 57, 5, "Añadir Canciones");
        desapareceMenuSeleccion();
        return true;
    }

    public void desapareceMenuSeleccion() {
        menuApp.getItem(0).setVisible(true);
        menuApp.getItem(1).setVisible(true);
        menuApp.getItem(2).setVisible(false);
        menuApp.getItem(3).setVisible(false);
    }

    public void apareceMenuSeleccion() {
        menuApp.getItem(0).setVisible(false);
        menuApp.getItem(1).setVisible(false);
        menuApp.getItem(2).setVisible(true);
        menuApp.getItem(3).setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //ID añadir canciones
            case 57:
                if (mSwipeViewPagerAdapter.getNombresListas().isEmpty()) {
                    Toast.makeText(this, "No tienes listas creadas", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Pulsado añadir", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.itemCrearLista:
                mSwipeViewPagerAdapter.crearLista(mViewPager);
                return true;
            case R.id.itemBorrar:
                return true;
            case R.id.itemCancelar:
                cancelarSeleccion();
                return true;
            case R.id.itemBuscarLista:
                SubMenu sMenu = item.getSubMenu();
                sMenu.clear();
                if (mSwipeViewPagerAdapter.getNombresListas().isEmpty()) {
                    Toast.makeText(this, "No tienes listas creadas", Toast.LENGTH_SHORT).show();
                } else {
                    ArrayList<String> listas = mSwipeViewPagerAdapter.getNombresListas();
                    int idg = 1,
                            id = 1,
                            orden = 0;
                    for (String nombre : listas) {
                        sMenu.add(idg, id, orden, nombre);
                        MenuItem sItem = sMenu.getItem(orden);
                        sItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            public boolean onMenuItemClick(MenuItem item) {
                                mViewPager.setCurrentItem(item.getOrder(), true);
                                return true;
                            }
                        });
                        idg++;
                        id++;
                        orden++;
                    }
                }
                return true;
            case R.id.itemBorrarLista:
                if (mSwipeViewPagerAdapter.getNombresListas().isEmpty()) {
                    Toast.makeText(this, "No tienes listas creadas", Toast.LENGTH_SHORT).show();
                } else {
                    int pestanaBorrar = mViewPager.getCurrentItem();
                    mViewPager.setCurrentItem(pestanaBorrar - 1, true);
                    mSwipeViewPagerAdapter.borrarLista(mViewPager, pestanaBorrar);
                    mViewPager.setOffscreenPageLimit(mSwipeViewPagerAdapter.getCount());
                    mSwipeViewPagerAdapter.notifyDataSetChanged();
                }
                return true;
            //ID de promocionar
            case 58:
                if (mSwipeViewPagerAdapter.getNombresListas().isEmpty()) {
                    Toast.makeText(this, "No tienes listas creadas", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Pulsado Promocionar", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
