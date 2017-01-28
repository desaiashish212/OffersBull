package com.offersbull;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.offersbull.activity.AutomobileActivity;
import com.offersbull.activity.HotelRestroActivity;
import com.offersbull.activity.OtherActivity;
import com.offersbull.activity.RealEstateActivity;
import com.offersbull.activity.TravellingActivity;
import com.offersbull.activity.TutionActivity;
import com.offersbull.fragment.Home;
import com.offersbull.model.Travelling;
import com.offersbull.model.Tution;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Drawer result;
    PrimaryDrawerItem item1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


        Home home = (Home) getSupportFragmentManager().findFragmentByTag("newsFrag");
        if(home == null) {
            home = new Home();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main_frame, home, "newsFrag");
            ft.commit();
        }

        item1 = new PrimaryDrawerItem().withName("SingleItemModel").withIcon(FontAwesome.Icon.faw_newspaper_o);

        result = new DrawerBuilder(this)
                //this layout have to contain child layouts
              //  .withRootView(R.id.drawer_container)
                .withToolbar(toolbar)
                .withHeader(R.layout.header)
                .withDisplayBelowStatusBar(true)
                .withFullscreen(false)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(
                        item1,
                        new PrimaryDrawerItem().withName("Real Estate").withIcon(FontAwesome.Icon.faw_home).withIdentifier(2000),
                        new PrimaryDrawerItem().withName("Tution").withIcon(FontAwesome.Icon.faw_file_text_o).withIdentifier(2001),
                        new PrimaryDrawerItem().withName("Hotels & Restaurents").withIcon(FontAwesome.Icon.faw_bed).withIdentifier(2002),
                        new PrimaryDrawerItem().withName("Travelling").withIcon(FontAwesome.Icon.faw_plane).withIdentifier(2003),
                        new PrimaryDrawerItem().withName("Automobile").withIcon(FontAwesome.Icon.faw_car).withIdentifier(2004),
                        new PrimaryDrawerItem().withName("Other").withIcon(FontAwesome.Icon.faw_plus_circle).withIdentifier(2005)
               )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        switch (position){
                            case 1:
                                Home home = (Home) getSupportFragmentManager().findFragmentByTag("newsFrag");
                                if(home == null) {
                                    home = new Home();
                                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                    ft.replace(R.id.content_main_frame, home, "newsFrag");
                                    ft.commit();
                                }
                                result.closeDrawer();
                                break;
                           /* case 2:
                                Toast.makeText(MainActivity.this,"Real Estate",Toast.LENGTH_SHORT).show();
                                result.closeDrawer();
                                break;
                            case 3:
                                Toast.makeText(MainActivity.this,"Tution",Toast.LENGTH_SHORT).show();
                                result.closeDrawer();
                                break;
                            case 4:
                                Toast.makeText(MainActivity.this,"Hotels & Restaurents",Toast.LENGTH_SHORT).show();
                                result.closeDrawer();
                                break;
                            case 5:
                                Toast.makeText(MainActivity.this,"Travelling",Toast.LENGTH_SHORT).show();
                                result.closeDrawer();
                                break;
                            case 6:
                                Toast.makeText(MainActivity.this,"Automobile",Toast.LENGTH_SHORT).show();
                                result.closeDrawer();
                                break;
                            case 7:
                                Toast.makeText(MainActivity.this,"Other",Toast.LENGTH_SHORT).show();
                                result.closeDrawer();
                                break;

                               */

                        }

                        if (drawerItem != null) {
                            if (drawerItem.getIdentifier() == 2000) {
                                startActivity(new Intent(MainActivity.this,RealEstateActivity.class));
                                // Toast.makeText(DrawerActivity.this,"news",Toast.LENGTH_SHORT).show();
                                result.closeDrawer();
                                result.setSelection(1,true);

                            } else if (drawerItem.getIdentifier() == 2001) {
                                startActivity(new Intent(MainActivity.this,TutionActivity.class));
                                // Toast.makeText(DrawerActivity.this,"event",Toast.LENGTH_SHORT).show();
                                result.closeDrawer();
                                result.setSelection(1,true);
                            }
                            else if (drawerItem.getIdentifier() == 2002) {
                                startActivity(new Intent(MainActivity.this,HotelRestroActivity.class));
                                // Toast.makeText(DrawerActivity.this,"event",Toast.LENGTH_SHORT).show();
                                result.closeDrawer();
                                result.setSelection(1,true);
                            }else if (drawerItem.getIdentifier() == 2003) {
                                startActivity(new Intent(MainActivity.this,TravellingActivity.class));
                                // Toast.makeText(DrawerActivity.this,"event",Toast.LENGTH_SHORT).show();
                                result.closeDrawer();
                                result.setSelection(1,true);
                            }else if (drawerItem.getIdentifier() == 2004) {
                                startActivity(new Intent(MainActivity.this,AutomobileActivity.class));
                                // Toast.makeText(DrawerActivity.this,"event",Toast.LENGTH_SHORT).show();
                                result.closeDrawer();
                                result.setSelection(1,true);
                            }else if (drawerItem.getIdentifier() == 2005) {
                                startActivity(new Intent(MainActivity.this,OtherActivity.class));
                                // Toast.makeText(DrawerActivity.this,"event",Toast.LENGTH_SHORT).show();
                                result.closeDrawer();
                                result.setSelection(1,true);
                            }

                        }

                        return true;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();




    }
}
