package edu.proyectos.smartpathv3;

import android.app.FragmentManager;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //DECLARACIONES
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    private CharSequence activityTitle;
    private CharSequence itemTitle;
    private String[] listOfOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemTitle=activityTitle=getTitle();
        //Obtener arreglo de strings desde los recursos
        listOfOptions = getResources().getStringArray(R.array.listOfOptions);
        //Obtener drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //Obtener listview
        drawerList = (ListView) findViewById(R.id.left_drawer);

        //anadir una sombra sobre el contendio principal cuando el drawer se despliegue
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        //Nueva lista de drawer items
        ArrayList<DrawerItem> items = new ArrayList<DrawerItem>();
        items.add(new DrawerItem(listOfOptions[0],R.drawable.ic_travel));
        items.add(new DrawerItem(listOfOptions[1],R.drawable.ic_travels));
        items.add(new DrawerItem(listOfOptions[2],R.drawable.ic_path));
        items.add(new DrawerItem(listOfOptions[3],R.drawable.ic_account));
        items.add(new DrawerItem(listOfOptions[4],R.drawable.ic_help));

        // Relacionar el adaptador y la escucha de la lista del drawer
        drawerList.setAdapter(new DrawerListAdapter(this, items));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        //Habilitar el icono de la app pr si hay algun estilo que lo deshabilito
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Crear ActionBarToggle para la apertura y cierre
        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close
        ){
            public void onDrawerClosed(View view){
                getSupportActionBar().setTitle(itemTitle);

                //Este usarlo si modifico la action bar en cada fragmento
                //invalidateOptionsMenu();
            }
            public void onDrawerOpened(View drawerView){
                getSupportActionBar().setTitle(activityTitle);
            }
            //setear la escucha
        };
        drawerLayout.setDrawerListener(drawerToggle);

        if (savedInstanceState == null)
            selectItem(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (drawerToggle.onOptionsItemSelected(item)){
            //todos los eventos de seleccion del toogle van aqui
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Laa escucha del ListView En el Drawer
     */
    private class DrawerItemClickListener implements ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,long id){
            selectItem(position);
        }
    }

    private void selectItem(int position){
        //Remplazar el contenido del layout principal por un fragmento
        switch (position){
            case 0:
                TravelFragment travelFragment = new TravelFragment();
                Bundle bundle= new Bundle();
                bundle.putInt(travelFragment.ARG_ARTICLES_NUMBER,position);
                travelFragment.setArguments(bundle);

                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame,travelFragment).commit();

                //se actualiza el item seleccionado y el titulo , despues de cerrar el drawer
                drawerList.setItemChecked(position,true);
                setTitle(listOfOptions[position]);
                drawerLayout.closeDrawer(drawerList);
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            /* // Reemplazar el contenido del layout principal por un fragmento
            ArticleFragment fragment = new ArticleFragment();
            Bundle args = new Bundle();
            args.putInt(ArticleFragment.ARG_ARTICLES_NUMBER, position);
            fragment.setArguments(args);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            // Se actualiza el item seleccionado y el título, después de cerrar el drawer
            drawerList.setItemChecked(position, true);
            setTitle(tagTitles[position]);
            drawerLayout.closeDrawer(drawerList);
            */
        }
    }

    /*metodo auxiliar para setear titulo en la action bar*/
    @Override
    public void setTitle(CharSequence title){
        itemTitle=title;
        getSupportActionBar().setTitle(itemTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        //sincronizar el estado del drawer
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        //cambiar las configuraciones del drawer si hubo modificaciones
        drawerToggle.onConfigurationChanged(newConfig);
    }
}

