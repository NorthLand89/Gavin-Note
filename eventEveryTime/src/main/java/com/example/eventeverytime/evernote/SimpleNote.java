/*
 * Copyright 2012 Evernote Corporation
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.example.eventeverytime.evernote;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.evernote.client.android.EvernoteSession;
import com.evernote.client.android.EvernoteUtil;
import com.evernote.client.android.OnClientCallback;
import com.evernote.edam.type.Note;
import com.evernote.edam.type.Notebook;
import com.evernote.thrift.transport.TTransportException;
import com.example.eventeverytime.R;

import java.util.List;

/**
 * 用于发送笔记的Activity
 * 
 *
 */
public class SimpleNote extends ParentActivity {



  private static final String LOGTAG = "SimpleNote";

  private EditText mEditTextTitle;
  private EditText mEditTextContent;
  private Button mBtnSave;
  private Button mBtnSelect;

  private String mSelectedNotebookGuid;

  // Callback used as a result of creating a note in a normal notebook or a linked notebook
  private OnClientCallback<Note> mNoteCreateCallback = new OnClientCallback<Note>() {
    @Override
    public void onSuccess(Note note) {
      Toast.makeText(getApplicationContext(),"保存成功", Toast.LENGTH_LONG).show();
      
      removeDialog(DIALOG_PROGRESS);
      finish();
    }

    @Override
    public void onException(Exception exception) {
      Log.e(LOGTAG, "Error saving note", exception);
      Toast.makeText(getApplicationContext(), "保存失败", Toast.LENGTH_LONG).show();
      removeDialog(DIALOG_PROGRESS);
      finish();
    }
  };

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mEvernoteSession = EvernoteSession.getInstance(this, CONSUMER_KEY, CONSUMER_SECRET, EVERNOTE_SERVICE, SUPPORT_APP_LINKED_NOTEBOOKS);
    
    Note note = (Note) getIntent().getSerializableExtra("note");
    saveNote(note);
  }
/**
 * 保存笔记
 * @param note
 */

  public void saveNote(Note note) {

	  if(!mEvernoteSession.isLoggedIn()){
		  Toast.makeText(getApplicationContext(), "尚未绑定至印象笔记账号", Toast.LENGTH_LONG).show();
		  finish();
		  return;
		  
	  }
    if(!mEvernoteSession.getAuthenticationResult().isAppLinkedNotebook()) {
      //If User has selected a notebook guid, assign it now
      if (!TextUtils.isEmpty(mSelectedNotebookGuid)) {
        note.setNotebookGuid(mSelectedNotebookGuid);
      }
      showDialog(DIALOG_PROGRESS);
      try {
        mEvernoteSession.getClientFactory().createNoteStoreClient().createNote(note, mNoteCreateCallback);
      } catch (TTransportException exception) {
        Log.e(LOGTAG, "Error creating notestore", exception);
        Toast.makeText(getApplicationContext(), "创建笔记失败", Toast.LENGTH_LONG).show();
        removeDialog(DIALOG_PROGRESS);
      }
    } else {
      super.createNoteInAppLinkedNotebook(note, mNoteCreateCallback);
    }
  }


}
