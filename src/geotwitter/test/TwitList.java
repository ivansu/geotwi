package geotwitter.test;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TwitList extends ArrayList<TwitInfo> {

	public int GetCount() {
		int i = 0;
		for (TwitInfo t : this) {
			i++;
		}
		return i;
	}

	public static TwitList GetListByJSON(double lat, double lng, double km) {
		TwitList _list = new TwitList();

		String results = Utility.GetHttpFromServer(lat, lng, km);

		if (results != null) {
			if (results.compareTo("Error") != 0) {
				// parse json data
				try {
					// JSONArray jArray = new JSONArray(results);
					JSONArray twitResult = null;
					// for (int i = 0; i < jArray.length(); i++) {
					JSONObject json_root = new JSONObject(results);
					// JSONObject json_root = jArray.getJSONObject(i);
					twitResult = json_root.getJSONArray("results");
					for (int k = 0; k < twitResult.length(); k++) {
						JSONObject json_twit = twitResult.getJSONObject(k);

						TwitInfo _t = new TwitInfo();
						if (!json_twit.isNull("created_at")) {
							_t.SetCreatedDateTime(json_twit
									.getString("created_at"));
						}
						if (!json_twit.isNull("from_user")) {
							_t.SetFromUser(json_twit.getString("from_user"));
						}
						if (!json_twit.isNull("from_user_id")) {
							_t.SetFromUserID(json_twit.getInt("from_user_id"));
						}
						if (!json_twit.isNull("from_user_name")) {
							_t.SetFromUserName(json_twit
									.getString("from_user_name"));
						}
						if (!json_twit.isNull("id")) {
							_t.SetID(json_twit.getLong("id"));
						}

						JSONArray geoCoordinates = null;
						if (!json_twit.isNull("geo")) {
							JSONObject json_geo = json_twit
									.getJSONObject("geo");
							geoCoordinates = json_geo
									.getJSONArray("coordinates");

							_t.SetGeoLat(geoCoordinates.getDouble(0));
							_t.SetGeoLng(geoCoordinates.getDouble(1));
						}
						if (!json_twit.isNull("text")) {
							_t.SetText(json_twit
									.getString("text"));
						}
						_list.add(_t);
					}

					// }
				} catch (JSONException e) {
					return _list;
					// Log.e("log_tag", "Error parsing data "+e.toString());
				}
			}
		}

		return _list;
	}
}
