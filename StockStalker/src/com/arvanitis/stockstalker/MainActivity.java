package com.arvanitis.stockstalker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;
	
	String id;
	List<Item> arrayOfList;
	static ListView listView;
	ProgressDialog pDialog = null;
	
	private static final String feed = "https://query.yahooapis.com/v1/public/yql?q=select%20symbol,Name,LastTradePriceOnly,AskRealTime,BidRealTime,ChangeinPercent,LastTradeDate,LastTradeTime%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(%22aapl%22,%22bac%22,%22twtr%22,%22fb%22,%22goog%22,%22lnkd%22,%22msft%22,%22%5EGDAXI%22,%22CAC%22,%22DIA%22,%22%5EIXIC%22,%22EURUSD%3DX%22,%22%5EFTSE%22,%22C%22)&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
		
		new MyTask().execute();
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager
				.beginTransaction()
				.replace(R.id.container,
						PlaceholderFragment.newInstance(position + 1)).commit();
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_section1);
			//System.out.println("Yes it is!!!");
			break;
		case 2:
			mTitle = getString(R.string.title_section2);
			break;
		case 3:
			mTitle = getString(R.string.title_section3);
			break;
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			TextView textView = (TextView) rootView
					.findViewById(R.id.section_label);
			textView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));
			
			listView = (ListView) rootView.findViewById(R.id.list1); 
			
			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((MainActivity) activity).onSectionAttached(getArguments().getInt(
					ARG_SECTION_NUMBER));
		}
	}
	
	class MyTask extends AsyncTask<String, String, String> { 
        
        @Override
        protected void onPreExecute() { 
            super.onPreExecute(); 
            pDialog = new ProgressDialog(MainActivity.this); 
            pDialog.setMessage("Φόρτωση..."); 
            pDialog.show(); 
        } 
          
        @Override
        protected String doInBackground(String... params) {
        	//connectToService(feed, null);
            arrayOfList = new NamesParser().getData(feed,id); 
            return id; 
        } 
          
        @Override
        protected void onPostExecute(String result) { 
            super.onPostExecute(result); 
  
            if (null != pDialog && pDialog.isShowing()) { 
                pDialog.dismiss(); 
            } 
  
            if (null == arrayOfList || arrayOfList.size() == 0) { 
                //showToast("Δεν βρέθηκαν δεδομένα!"); 
                //Listactivity.this.finish(); 
            } else { 
  
                // check data... 
                /* 
                 * for (int i = 0; i < arrayOfList.size(); i++) { Item item = 
                 * arrayOfList.get(i); System.out.println(item.getId()); 
                 * System.out.println(item.getTitle()); 
                 * System.out.println(item.getDesc()); 
                 * System.out.println(item.getPubdate()); 
                 * System.out.println(item.getLink()); } 
                 */
  
                setAdapterToListview(); 
  
            } 
  
        } 
    } 
      
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, 
            long id) { 
        Item item = arrayOfList.get(position); 
        Intent intent = new Intent(MainActivity.this, StockActivity.class); 
        intent.putExtra("url", item.getLink()); 
        intent.putExtra("title", item.getTitle()); 
        intent.putExtra("addres", item.getAddress()); 
        intent.putExtra("pubDate", item.getPubdate()); 
        MainActivity.this.finish(); 
        startActivity(intent); 
    } 
      
    public void setAdapterToListview() { 
        NewsRowAdapter objAdapter = new NewsRowAdapter(MainActivity.this, 
                R.layout.list, arrayOfList,id); 
        System.out.println("Adapter is :" + objAdapter.getCount() + "\nList is : " + arrayOfList.get(1)); 
        listView.setAdapter(objAdapter); 
    }
    
    
    public JSONObject connectToService(String url, final Context context ) {

        try {
            if(!isConnected(context)){
                return null;
            }

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);

            HttpParams httpParameters = httpGet.getParams();
            // Set the timeout in milliseconds until a connection is established.
            int timeoutConnection = 7500;
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
            // Set the default socket timeout (SO_TIMEOUT) 
            // in milliseconds which is the timeout for waiting for data.
            int timeoutSocket = 7500;
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            InputStream is = httpEntity.getContent();

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();

            String jsonString = sb.toString();
            Log.e("WebServiceHandler", "JSON string returned is"+jsonString.toString());

            JSONObject jsonObject = new JSONObject(jsonString);
            return jsonObject;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        } catch (ConnectTimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

          return null;
    }

    public boolean isConnected(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) 
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

}
