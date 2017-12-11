package com.yifei.mytimetable;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;



public class EmailActivity extends Activity implements OnClickListener{
  Button send;
  Button cancel;
  EditText title_edit;
  EditText field_edit;
  EditText receiver_edit;
  String title;
  String receiver;
  String field;
	@TargetApi(Build.VERSION_CODES.CUPCAKE)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.email);

		receiver_edit=(EditText)findViewById(R.id.receiver_edit);
		send=(Button)findViewById(R.id.send_button);
		cancel=(Button)findViewById(R.id.cancelsend_button);
		send.setOnClickListener(this);
		cancel.setOnClickListener(this);

		Intent x=getIntent();
		receiver=x.getStringExtra("email");
		Log.i("EmailActivity",receiver);
		receiver_edit.setText(receiver);
	}
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.send_button:

			sendEmail();
			break;
		case R.id.cancelsend_button:
			Intent x=new Intent(this,CourseActivity.class);
			x.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(x);
			break;
		}
	}
	private void sendEmail() {
		// TODO Auto-generated method stub


		Intent intent = new Intent();  
		intent.setType("text/plain");

		intent.setAction(Intent.ACTION_SEND);

		intent.putExtra(Intent.EXTRA_EMAIL, new String[]    
		{receiver});   


		try{
			startActivity(Intent.createChooser(intent, "SENDING EMAIL..."));
			finish();
		 }catch (android.content.ActivityNotFoundException ex) {
			    Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		 }
		
	}

}
