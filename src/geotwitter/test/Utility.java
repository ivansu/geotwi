package geotwitter.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

public class Utility {
	public static final String UrlAddress = "http://search.twitter.com/search.json?rpp=100&result_type=recent&show_user=true&geocode=";
	public static final String UrlAddressEnd = "km";
	
	public static String GetHttpFromServer(
			double lat, double lng, double km) {
		String results = null;

		String geo = String.valueOf(lat) + "," + String.valueOf(lng) + "," + String.valueOf(km);
		String fullAddress = UrlAddress + geo + UrlAddressEnd;
		
		InputStream is = null;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(fullAddress);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			return "Error";
		}

		if (is == null) {
			return results;
		}
		// convert response to string
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();

			results = sb.toString();
		} catch (Exception e) {
			return "Error";
		}

		return results;
	}
	
	public static Location getLocation(Context ctx) {
	    LocationManager lm = (LocationManager) ctx
	            .getSystemService(Context.LOCATION_SERVICE);
	    List<String> providers = lm.getProviders(true);
	    Location l = null;
	    Criteria criteria = new Criteria();
        String bestProvider = lm.getBestProvider(criteria, false);
        l = lm.getLastKnownLocation(bestProvider);
        if(l != null){
        	return l;
        }
	    for (int i = providers.size() - 1; i >= 0; i--) {
	        l = lm.getLastKnownLocation(providers.get(i));
	        if (l != null)
	            break;
	    }
	    return l;
	}
}
