package com.example.eventeverytime.alarm;

import com.example.eventeverytime.R;
import com.example.eventeverytime.factory.DialogFactory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

/**
 *
 */
public class AddEventActivity extends Activity {
	String number;
	String note;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	
	Intent intent = getIntent();
	
	if(Intent.ACTION_SEND.equals(intent.getAction())){
	note = intent.getStringExtra(Intent.EXTRA_TEXT);
	number=intent.getStringExtra(Intent.EXTRA_TITLE);
	}else{
	number = intent.getStringExtra("extra");
	note = intent.getStringExtra("note");
	}
	new DialogFactory(AddEventActivity.this).addEventDialog(number,note);
}

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
protected void onResume() {
	// TODO Auto-generated method stub
    super.onResume();
}
}
