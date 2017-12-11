package com.yifei.mytimetable;

import java.util.ArrayList;

import android.app.*;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


public class AseAdapter extends BaseAdapter{
	private Context viewContext;
	Activity activity;
	private ArrayList<TextView> list=new ArrayList<TextView>();
	private ArrayList<Schedule> schedule=new ArrayList<Schedule>();
	private SQLiteDatabase db;
    public AseAdapter(Context x){
    	openDatabase();
    	viewContext=x;
    	initList();
    }
    public ArrayList<TextView> getList(){
    	return list;
    }
    private void openDatabase() {
        try {        	
        	db = SQLiteDatabase.openDatabase(   		 
        			"sdcard/homework",
    				null,
    				SQLiteDatabase.CREATE_IF_NECESSARY) ;       	
        	
        	//Toast.makeText(this, "DB was opened!", 1).show();
        }
        catch (SQLiteException e) {
        	 Log.i("ASEADAPTER", e.getMessage())   ;   	
        }
    }//createDatabase
	private void initList() {
		// TODO Auto-generated method stub
		initSchedule();
		activity=(Activity) viewContext;
		for(int k=0;k<54;k++){
		  TextView text=new TextView(viewContext);
		  text.setTextSize(10);
		  text.setBackgroundColor(Color.LTGRAY);
		  text.setTextColor(Color.RED);
		  text.setHeight(40);
		  text.setGravity(Gravity.CENTER);
		  //text.setLayoutParams(new GridView.LayoutParams(45, 45));
		  text.setPadding(1, 1, 1,1);
		  list.add(text);
		}

		int k=6;
		for(int i=0;i<8;i++){
			for(int j=0;j<5;j++){
				int p=j+k+1;
				list.get(p).setTextColor(Color.BLACK);
				list.get(p).setTextSize(14);
			}
			k+=6;
		}
		list.get(1).setText("MON");
		list.get(2).setText("TUE");
		list.get(3).setText("WED");
		list.get(4).setText("THU");
		list.get(5).setText("FRI");
		list.get(6).setText("1ST");
		list.get(12).setText("2ND");
		list.get(18).setText("3RD");
		list.get(24).setText("4TH");
		list.get(30).setText("5TH");
		list.get(36).setText("6TH");
		list.get(42).setText("7TH");
		list.get(48).setText("8TH");
		

		for(int i=0;i<schedule.size();i++){
			Schedule s=schedule.get(i);
			String date=s.getDate();
			if(date.equals("MON")){
				list.get(7).setText(s.getOne());
				list.get(13).setText(s.getTwo());
				list.get(19).setText(s.getThree());
				list.get(25).setText(s.getFour());
				list.get(31).setText(s.getFive());
				list.get(37).setText(s.getSix());
				list.get(43).setText(s.getSeven());
				list.get(49).setText(s.getEight());
			}
			if(date.equals("TUE")){
				list.get(8).setText(s.getOne());
				list.get(14).setText(s.getTwo());
				list.get(20).setText(s.getThree());
				list.get(26).setText(s.getFour());
				list.get(32).setText(s.getFive());
				list.get(38).setText(s.getSix());
				list.get(44).setText(s.getSeven());
				list.get(50).setText(s.getEight());
			}
			if(date.equals("WED")){
				list.get(9).setText(s.getOne());
				list.get(15).setText(s.getTwo());
				list.get(21).setText(s.getThree());
				list.get(27).setText(s.getFour());
				list.get(33).setText(s.getFive());
				list.get(39).setText(s.getSix());
				list.get(45).setText(s.getSeven());
				list.get(51).setText(s.getEight());
			}
			if(date.equals("THU")){
				list.get(10).setText(s.getOne());
				list.get(16).setText(s.getTwo());
				list.get(22).setText(s.getThree());
				list.get(28).setText(s.getFour());
				list.get(34).setText(s.getFive());
				list.get(40).setText(s.getSix());
				list.get(46).setText(s.getSeven());
				list.get(52).setText(s.getEight());
			}
			if(date.equals("FRI")){
				list.get(11).setText(s.getOne());
				list.get(17).setText(s.getTwo());
				list.get(23).setText(s.getThree());
				list.get(29).setText(s.getFour());
				list.get(35).setText(s.getFive());
				list.get(41).setText(s.getSix());
				list.get(47).setText(s.getSeven());
				list.get(53).setText(s.getEight());
			}
		}

	}
	private void initSchedule() {
		// TODO Auto-generated method stub
		String sql="select * from schedule;";
		Cursor cur=db.rawQuery(sql, null);
		if(cur.moveToFirst()){
			int date_int=cur.getColumnIndex("date");
			int one_int=cur.getColumnIndex("one");
			int two_int=cur.getColumnIndex("two");
			int three_int=cur.getColumnIndex("three");
			int four_int=cur.getColumnIndex("four");
			int five_int=cur.getColumnIndex("five");
			int six_int=cur.getColumnIndex("six");
			int seven_int=cur.getColumnIndex("seven");
			int eight_int=cur.getColumnIndex("eight");
			String date_str="";
			String one_str="";
			String two_str="";
			String three_str="";
			String four_str="";
			String five_str="";
			String six_str="";
			String seven_str="";
			String eight_str="";
			
			do{
				date_str=cur.getString(date_int);
				one_str=cur.getString(one_int);
				two_str=cur.getString(two_int);
				three_str=cur.getString(three_int);
				four_str=cur.getString(four_int);
				five_str=cur.getString(five_int);
				six_str=cur.getString(six_int);
				seven_str=cur.getString(seven_int);
				eight_str=cur.getString(eight_int);
				Schedule s=new Schedule();
				s.setDate(date_str);
				s.setOne(one_str);
			    s.setTwo(two_str);
			    s.setThree(three_str);
			    s.setFour(four_str);
			    s.setFive(five_str);
			    s.setSix(six_str);
			    s.setSeven(seven_str);
			    s.setEight(eight_str);
			    schedule.add(s);
			}while(cur.moveToNext());
		}
		db.close();
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TextView textview;
		if(convertView==null){
			textview=list.get(position);
            
		}else{
			textview=(TextView) convertView;
		}
		return textview;
	} 

}
