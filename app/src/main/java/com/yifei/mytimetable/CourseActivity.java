package com.yifei.mytimetable;


import java.util.ArrayList;
import java.util.Calendar;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnKeyListener;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;



public class CourseActivity extends Activity {
    /** Called when the activity is first created. */
	GridView gridview;
	Button btn;
	View menuView;
	AlertDialog	menuDialog ;
	private SQLiteDatabase db;
	private static final int  ITEM1=Menu.FIRST;
	private static final int  ITEM2=Menu.FIRST+1;

	AseAdapter ase;
	ArrayList<TextView> courselist;
	int index=0;
    @SuppressLint("LongLogTag")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.grid);
        menuView = View.inflate(this, R.layout.main, null);
    	

		menuDialog = new AlertDialog.Builder(this).create();

		Window window = menuDialog.getWindow();

		WindowManager.LayoutParams lp = window.getAttributes();
		window .setGravity(Gravity.LEFT | Gravity.TOP);
		lp.x = 0;
		lp.y = 100;
		lp.width = 200;
		lp.height = 400;

		window .setAttributes(lp);
		menuDialog.setView(menuView);
		btn=(Button) findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				menuDialog.show();
			}
		});
        gridview=(GridView) menuView.findViewById(R.id.gridview);
        registerForContextMenu(gridview);
        try {
			openDatabase();		//open (create if needed) database
			initTable();	
			db.close();			//make sure to release the DB
		} catch (Exception e) {
			Log.i("CourseActivity-exception", e.getMessage());
		}   
		ase=new AseAdapter(this);
        gridview.setAdapter(ase);
        gridview.setOnItemClickListener(new AseListener(this));
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
        	 Toast.makeText(this, e.getMessage(), 1).show();        	
        }
    }//createDatabase
    @SuppressLint("LongLogTag")
	private void initTable() {
    	//create table: tblAmigo
    	db.beginTransaction();
		try {

			db.execSQL("create table schedule ("
					+ "sid integer PRIMARY KEY autoincrement, " 
			        + " date  text, " 
			        +" one text, "
			        +" two text, "
			        +"three text,"
			        + "four text,"+"five text,"+"six text,"+"seven text,"+"eight text);");
			String sql="create table course(lid integer PRIMARY KEY autoincrement,coursename text,teacher text,location text,email text);";
			db.execSQL(sql);
			//commit your changes 
    		db.setTransactionSuccessful();
    		
			//Toast.makeText(this, "Table was created",1).show();
		} catch (SQLException e1) {			
			//Toast.makeText(this, e1.getMessage(),1).show();
			Log.i("CourseActivity-exception", e1.getMessage());
		}
		finally {
    		db.endTransaction();
    	}

    }
    

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0,ITEM1,0,"HELP");
		menu.add(0,ITEM2,0,"EXIT");
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case ITEM1:
			Intent x=new Intent(this,HelpActivity.class);
			startActivity(x);
			break;
		case ITEM2:
			this.finish();
			break;
		}
		return true;
	}
	class AseListener implements OnItemClickListener{
		Context context;
       public AseListener(Context x){
    	   context=x;
       }
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if (arg2 == 0 || arg2 == 1 || arg2 == 2 || arg2 == 3 || arg2 == 4
					|| arg2 == 5 || arg2 == 6 || arg2 == 12 || arg2 == 18
					|| arg2 == 24|| arg2 == 30|| arg2 == 36|| arg2 == 42|| arg2 == 48){
				return;
			}
			else{
			 index=arg2;
			 courselist=ase.getList();
			 GetDialogReturnListener listener=new GetDialogReturnListener();
			 AseDialog dia=new AseDialog(context,listener);
			 dia.setCouseName(courselist.get(arg2).getText().toString().trim());
			 dia.show();
			}
		}
		
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		return super.onCreateDialog(id);
	}
	class GetDialogReturnListener implements AseInterface{

		public void saveClick(String name) {
			// TODO Auto-generated method stub
			openDatabase();

			courselist.get(index).setText(name);

			if(index==7||index==13||index==19||index==25||index==31||index==37||index==43||index==49){

				String sql="select * from schedule where date='MON';";
				Cursor cur=db.rawQuery(sql, null);
				db.beginTransaction();
				try{
				  if(cur.moveToFirst()){
					 //update
					String update="";
					switch(index){
					 case 7:
						 update="update schedule set one='"+name+"' where date='MON';";
					     break;
					 case 13:
						 update="update schedule set two='"+name+"' where date='MON';";
						 break;
					 case 19:
						 update="update schedule set three='"+name+"' where date='MON';";
						 break;
					 case 25:
						 update="update schedule set four='"+name+"' where date='MON';";
						 break;
					 case 31:
						 update="update schedule set five='"+name+"' where date='MON';";
						 break;
					 case 37:
						 update="update schedule set six='"+name+"' where date='MON';";
						 break;
					 case 43:
						 update="update schedule set seven='"+name+"' where date='MON';";
						 break;
					 case 49:
						 update="update schedule set eight='"+name+"' where date='MON';";
						 break;
					}
					db.execSQL(update);
					db.setTransactionSuccessful();
				  }else{
					  //insert
					  String insert="";
					  switch(index){
					   case 7:
						 insert="insert into schedule (one,date) values ('"+name+"','MON');";
					    break;
					   case 13:
						 insert="insert into schedule (two,date) values ('"+name+"','MON');";
						 break;
					   case 19:
						 insert="insert into schedule (three,date) values ('"+name+"','MON');";
						 break;
					   case 25:
						 insert="insert into schedule (four,date) values ('"+name+"','MON');";
						 break;
					   case 31:
						   insert="insert into schedule (five,date) values ('"+name+"','MON');";
						   break;
					   case 37:
						   insert="insert into schedule (six,date) values ('"+name+"','MON');";
						   break;
					   case 43:
						   insert="insert into schedule (seven,date) values ('"+name+"','MON');";
						   break;
					   case 49:
						   insert="insert into schedule (eight,date) values ('"+name+"','MON');";
						   break;
					  }
					  db.execSQL(insert);
					  db.setTransactionSuccessful();
				  }
				}catch(SQLiteException x){
					Log.i("COURSEACTIVITY",x.getMessage());
				}finally{
					db.endTransaction();
				}
			}
			if(index==8||index==14||index==20||index==26||index==32||index==38||index==44||index==50){

				String sql="select * from schedule where date='TUE';";
				Cursor cur=db.rawQuery(sql, null);
				db.beginTransaction();
				try{
				  if(cur.moveToFirst()){
					 //update
					String update="";
					switch(index){
					 case 8:
						 update="update schedule set one='"+name+"' where date='TUE';";
					     break;
					 case 14:
						 update="update schedule set two='"+name+"' where date='TUE';";
						 break;
					 case 20:
						 update="update schedule set three='"+name+"' where date='TUE';";
						 break;
					 case 26:
						 update="update schedule set four='"+name+"' where date='TUE';";
						 break;
					 case 32:
						 update="update schedule set five='"+name+"' where date='TUE';";
						 break;
					 case 38:
						 update="update schedule set six='"+name+"' where date='TUE';";
						 break;
					 case 44:
						 update="update schedule set seven='"+name+"' where date='TUE';";
						 break;
					 case 50:
						 update="update schedule set eight='"+name+"' where date='TUE';";
						 break;
					}
					db.execSQL(update);
					db.setTransactionSuccessful();
				  }else{
					  //insert
					  String insert="";
					  switch(index){
					   case 8:
						 insert="insert into schedule (one,date) values ('"+name+"','TUE');";
					    break;
					   case 14:
						 insert="insert into schedule (two,date) values ('"+name+"','TUE');";
						 break;
					   case 20:
						 insert="insert into schedule (three,date) values ('"+name+"','TUE');";
						 break;
					   case 26:
						 insert="insert into schedule (four,date) values ('"+name+"','TUE');";
						 break;
					   case 32:
						   insert="insert into schedule (five,date) values ('"+name+"','TUE');";
						   break;
					   case 38:
						   insert="insert into schedule (six,date) values ('"+name+"','TUE');";
						   break;
					   case 44:
						   insert="insert into schedule (seven,date) values ('"+name+"','TUE');";
						   break;
					   case 50:
						   insert="insert into schedule (eight,date) values ('"+name+"','TUE');";
						   break;
					  }
					  db.execSQL(insert);
					  db.setTransactionSuccessful();
				  }
				}catch(SQLiteException x){
					Log.i("COURSEACTIVITY",x.getMessage());
				}finally{
					db.endTransaction();
				}
			}
			if(index==9||index==15||index==21||index==27||index==33||index==39||index==45||index==51){

				String sql="select * from schedule where date='WED';";
				Cursor cur=db.rawQuery(sql, null);
				db.beginTransaction();
				try{
				  if(cur.moveToFirst()){
					 //update
					String update="";
					switch(index){
					 case 9:
						 update="update schedule set one='"+name+"' where date='WED';";
					     break;
					 case 15:
						 update="update schedule set two='"+name+"' where date='WED';";
						 break;
					 case 21:
						 update="update schedule set three='"+name+"' where date='WED';";
						 break;
					 case 27:
						 update="update schedule set four='"+name+"' where date='WED';";
						 break;
					 case 33:
						 update="update schedule set five='"+name+"' where date='WED';";
						 break;
					 case 39:
						 update="update schedule set six='"+name+"' where date='WED';";
						 break;
					 case 45:
						 update="update schedule set seven='"+name+"' where date='WED';";
						 break;
					 case 51:
						 update="update schedule set eight='"+name+"' where date='WED';";
						 break;
					}
					db.execSQL(update);
					db.setTransactionSuccessful();
				  }else{
					  //insert
					  String insert="";
					  switch(index){
					   case 9:
						 insert="insert into schedule (one,date) values ('"+name+"','WED');";
					    break;
					   case 15:
						 insert="insert into schedule (two,date) values ('"+name+"','WED');";
						 break;
					   case 21:
						 insert="insert into schedule (three,date) values ('"+name+"','WED');";
						 break;
					   case 27:
						 insert="insert into schedule (four,date) values ('"+name+"','WED');";
						 break;
					   case 33:
						   insert="insert into schedule (five,date) values ('"+name+"','WED');";
						   break;
					   case 39:
						   insert="insert into schedule (six,date) values ('"+name+"','WED');";
						   break;
					   case 45:
						   insert="insert into schedule (seven,date) values ('"+name+"','WED');";
						   break;
					   case 51:
						   insert="insert into schedule (eight,date) values ('"+name+"','WED');";
						   break;
					  }
					  db.execSQL(insert);
					  db.setTransactionSuccessful();
				  }
				}catch(SQLiteException x){
					Log.i("COURSEACTIVITY",x.getMessage());
				}finally{
					db.endTransaction();
				}
			}
			if(index==10||index==16||index==22||index==28||index==34||index==40||index==46||index==52){
				String sql="select * from schedule where date='THU';";
				Cursor cur=db.rawQuery(sql, null);
				db.beginTransaction();
				try{
				  if(cur.moveToFirst()){
					 //update
					String update="";
					switch(index){
					 case 10:
						 update="update schedule set one='"+name+"' where date='THU';";
					     break;
					 case 16:
						 update="update schedule set two='"+name+"' where date='THU';";
						 break;
					 case 22:
						 update="update schedule set three='"+name+"' where date='THU';";
						 break;
					 case 28:
						 update="update schedule set four='"+name+"' where date='THU';";
						 break;
					 case 34:
						 update="update schedule set five='"+name+"' where date='THU';";
						 break;
					 case 40:
						 update="update schedule set six='"+name+"' where date='THU';";
						 break;
					 case 46:
						 update="update schedule set seven='"+name+"' where date='THU';";
						 break;
					 case 52:
						 update="update schedule set eight='"+name+"' where date='THU';";
						 break;
					}
					db.execSQL(update);
					db.setTransactionSuccessful();
				  }else{
					  //insert
					  String insert="";
					  switch(index){
					   case 10:
						 insert="insert into schedule (one,date) values ('"+name+"','THU');";
					    break;
					   case 16:
						 insert="insert into schedule (two,date) values ('"+name+"','THU');";
						 break;
					   case 22:
						 insert="insert into schedule (three,date) values ('"+name+"','THU');";
						 break;
					   case 28:
						 insert="insert into schedule (four,date) values ('"+name+"','THU');";
						 break;
					   case 34:
						   insert="insert into schedule (five,date) values ('"+name+"','THU');";
						   break;
					   case 40:
						   insert="insert into schedule (six,date) values ('"+name+"','THU');";
						   break;
					   case 46:
						   insert="insert into schedule (seven,date) values ('"+name+"','THU');";
						   break;
					   case 52:
						   insert="insert into schedule (eight,date) values ('"+name+"','THU');";
						   break;
					  }
					  db.execSQL(insert);
					  db.setTransactionSuccessful();
				  }
				}catch(SQLiteException x){
					Log.i("COURSEACTIVITY",x.getMessage());
				}finally{
					db.endTransaction();
				}
			}
			if(index==11||index==17||index==23||index==29||index==35||index==41||index==47||index==53){
				String sql="select * from schedule where date='FRI';";
				Cursor cur=db.rawQuery(sql, null);
				db.beginTransaction();
				try{
				  if(cur.moveToFirst()){
					 //update
					String update="";
					switch(index){
					 case 11:
						 update="update schedule set one='"+name+"' where date='FRI';";
					     break;
					 case 17:
						 update="update schedule set two='"+name+"' where date='FRI';";
						 break;
					 case 23:
						 update="update schedule set three='"+name+"' where date='FRI';";
						 break;
					 case 29:
						 update="update schedule set four='"+name+"' where date='FRI';";
						 break;
					 case 35:
						 update="update schedule set five='"+name+"' where date='FRI';";
						 break;
					 case 41:
						 update="update schedule set six='"+name+"' where date='FRI';";
						 break;
					 case 47:
						 update="update schedule set seven='"+name+"' where date='FRI';";
						 break;
					 case 53:
						 update="update schedule set eight='"+name+"' where date='FRI';";
						 break;
					}
					db.execSQL(update);
					db.setTransactionSuccessful();
				  }else{
					  //insert
					  String insert="";
					  switch(index){
					   case 11:
						 insert="insert into schedule (one,date) values ('"+name+"','FRI');";
					    break;
					   case 17:
						 insert="insert into schedule (two,date) values ('"+name+"','FRI');";
						 break;
					   case 23:
						 insert="insert into schedule (three,date) values ('"+name+"','FRI');";
						 break;
					   case 29:
						 insert="insert into schedule (four,date) values ('"+name+"','FRI');";
						 break;
					   case 35:
						   insert="insert into schedule (five,date) values ('"+name+"','FRI');";
						   break;
					   case 41:
						   insert="insert into schedule (six,date) values ('"+name+"','FRI');";
						   break;
					   case 47:
						   insert="insert into schedule (seven,date) values ('"+name+"','FRI');";
						   break;
					   case 53:
						   insert="insert into schedule (eight,date) values ('"+name+"','FRI');";
						   break;
					  }
					  db.execSQL(insert);
					  db.setTransactionSuccessful();
				  }
				}catch(SQLiteException x){
					Log.i("COURSEACTIVITY",x.getMessage());
				}finally{
					db.endTransaction();
				}
			}
			db.close();
		}

		public void cancelClick() {
			// TODO Auto-generated method stub

		}

		public void timechange(int time) {

		}

	}
}