package com.example.eventeverytime.evernote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.evernote.client.android.EvernoteSession;
import com.evernote.client.android.InvalidAuthenticationException;
import com.example.eventeverytime.R;



public class HelloEDAM extends ParentActivity {


  private static final String LOGTAG = "HelloEDAM";


  private Button mLoginButton;
  private Button mLogoutButton;
  private ListView mListView;
  private ArrayAdapter mAdapter;

  private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
     
          startActivity(new Intent(getApplicationContext(), SimpleNote.class));
        
      
    }
  };




  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    mLoginButton = (Button) findViewById(R.id.login);
    mLogoutButton = (Button) findViewById(R.id.logout);
    mListView = (ListView) findViewById(R.id.list);
    
    mListView.setOnItemClickListener(mItemClickListener);
  }

  
  
  @Override
  public void onResume() {
    super.onResume();

    updateAuthUi();
  }


  private void updateAuthUi() {
    mLoginButton.setEnabled(!mEvernoteSession.isLoggedIn());


    mListView.setEnabled(mEvernoteSession.isLoggedIn());
  }


  public void login(View view) {
    mEvernoteSession.authenticate(this);
  }

  public void logout(View view) {
    try {
      mEvernoteSession.logOut(this);
    } catch (InvalidAuthenticationException e) {
      Log.e(LOGTAG, "Tried to call logout with not logged in", e);
    }
    updateAuthUi();
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
      case EvernoteSession.REQUEST_CODE_OAUTH:
        if (resultCode == Activity.RESULT_OK) {
          updateAuthUi();
        }
        break;
    }
  }
}
