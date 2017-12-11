package com.yifei.mytimetable;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;



public class HelpActivity extends Activity implements OnClickListener{
  Button returnMenu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		returnMenu=(Button)findViewById(R.id.help_button);
		returnMenu.setOnClickListener(this);
	}
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent x=new Intent(this,CourseActivity.class);
		x.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(x);
	}
  
}
