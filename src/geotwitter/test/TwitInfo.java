package geotwitter.test;

public class TwitInfo {
	private long _id = 0;
	private String _created_at = "";
	private String _from_user = "";
	private int _from_user_id = 0;
	private String _from_user_name = "";
	private double _geoLat = 0.0;
	private double _geoLng = 0.0;
	private String _text = "";
	
	public String GetText(){
		return _text;
	}
	
	public void SetText(String value){
		_text = value;
	}
	
	public double GetGeoLng(){
		return _geoLng;
	}
	
	public void SetGeoLng(double value){
		_geoLng = value;
	}
	
	public double GetGeoLat(){
		return _geoLat;
	}
	
	public void SetGeoLat(double value){
		_geoLat = value;
	}
	
	public String GetFromUserName(){
		return _from_user_name;
	}
	
	public void SetFromUserName(String value){
		_from_user_name = value;
	}
	
	public int GetFromUserID(){
		return _from_user_id;
	}
	
	public void SetFromUserID(int value){
		_from_user_id = value;
	}
	
	public String GetFromUser(){
		return _from_user;
	}
	
	public void SetFromUser(String value){
		_from_user = value;
	}
	
	public String GetCreatedDateTime(){
		return _created_at;
	}
	
	public void SetCreatedDateTime(String value){
		_created_at = value;
	}
	
	public long GetID(){
		return _id;
	}
	
	public void SetID(long value){
		_id = value;
	}
	
	public String GetGeo(){
		return String.valueOf(_geoLat) + "," + String.valueOf(_geoLng);
	}
}
