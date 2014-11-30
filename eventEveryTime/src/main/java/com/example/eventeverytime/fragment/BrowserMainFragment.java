package com.example.eventeverytime.fragment;

import com.example.eventeverytime.bean.DataType;
import com.example.eventeverytime.bean.SpinerItemInfo;
import com.example.eventeverytime.factory.DetailViewFactory;
import com.example.eventeverytime.factory.Refreshable;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BrowserMainFragment extends Fragment implements Refreshable{

	SpinerItemInfo info;
	View view;
	public BrowserMainFragment(SpinerItemInfo info){
		this.info = info;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = new DetailViewFactory(
				info,
				getActivity()).getView();
		return view;
	}

	@Override
	public void refresh(SpinerItemInfo info) {
		Intent intent = new Intent("browserEvent");
		intent.putExtra("querry", info);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		getActivity().finish();
	}
	@Override
	public void onResume() {
		super.onResume();
	}
	
}
