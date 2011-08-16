package com.paust.Remote;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class TabWidget1 extends TabActivity {
    private int MAXTAB=0;
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.maintab);

	    Resources res = getResources(); // Resource object to get Drawables
	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	    Intent intent;  // Reusable Intent for each tab

	    // Create an Intent to launch an Activity for the tab (to be reused)
	    intent = new Intent().setClass(this, TelnetActivity.class);

	    // Initialize a TabSpec for each tab and add it to the TabHost
	    spec = tabHost.newTabSpec("alles").setIndicator("Alles"
	                      ,res.getDrawable(R.drawable.ic_tab_artists)
	                  ).setContent(intent);
	    tabHost.addTab(spec);

	    // Do the same for the other tabs
	    intent = new Intent().setClass(this, TextActivity.class);
	    spec = tabHost.newTabSpec("Text").setIndicator("Text"
	    		,  res.getDrawable(R.drawable.ic_tab_artists)
	    		).setContent(intent);
	    tabHost.addTab(spec);

	    intent = new Intent().setClass(this, RemoteActivity1.class);
	    spec = tabHost.newTabSpec("Remote").setIndicator("Remote"
	                  , res.getDrawable(R.drawable.ic_tab_artists)
	                  ).setContent(intent);
	    tabHost.addTab(spec);
	    
	    intent = new Intent().setClass(this, RemoteActivity2.class);
	    spec = tabHost.newTabSpec("Remote2").setIndicator("Remote2"
	                  , res.getDrawable(R.drawable.ic_tab_artists)
	                  ).setContent(intent);
	    tabHost.addTab(spec);
	    MAXTAB = 3;
	    tabHost.setCurrentTab(1);
	}

	 public void nexttab()
	 {  int i;
		    TabHost tabHost = getTabHost();  // The activity TabHost
		    i = tabHost.getCurrentTab();
		    i++;
		    if ( i > MAXTAB)
		    	i = 0;
		    tabHost.setCurrentTab(i);
	 }

	 public void prevtab()
	 {  int i;
		    TabHost tabHost = getTabHost();  // The activity TabHost
		    i = tabHost.getCurrentTab();
		    i--;
		    if ( i < 0)
		    	i = MAXTAB;
		    tabHost.setCurrentTab(i);
	 }
}
