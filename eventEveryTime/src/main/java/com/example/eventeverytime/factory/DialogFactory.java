package com.example.eventeverytime.factory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

import com.example.eventeverytime.R;
import com.example.eventeverytime.alarm.MyAlarmManager;
import com.example.eventeverytime.bean.*;
import com.example.eventeverytime.db.MyDB;
import com.example.eventeverytime.listadapters.MySpinerAdapter;
import com.example.eventeverytime.util.Jumper;
import com.example.eventeverytime.util.SpinnerTransFormer;

public class DialogFactory {
	boolean ischecked;
	int tempId;
	private AlertDialog dialog;
	final private Context context;
	SpinerItemInfo selectedProject;

	public DialogFactory(Context context) {
		this.context = context;
	}

	/*********************************************************
	 * 添加人物界面
	 */

	/**
	 * 人物对话框
	 */
	public void addPersonDialog(String... strings) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		View view = View.inflate(context, R.layout.add_person_dialog, null);

		final EditText nameEditText = (EditText) view
				.findViewById(R.id.et_add_person_name);
		if (strings.length == 1) {
			nameEditText.setText(strings[0]);
		}
		final EditText phoneEditText = (EditText) view
				.findViewById(R.id.et_add_person_phone);
		final EditText mobilePhoneEditText = (EditText) view
				.findViewById(R.id.et_add_person_mobilePhone);
		final EditText emailEditText = (EditText) view
				.findViewById(R.id.et_add_person_email);
		final EditText positionEditText = (EditText) view
				.findViewById(R.id.et_add_person_position);
		final EditText noteEditText = (EditText) view
				.findViewById(R.id.et_add_person_note);
		final Spinner companySpinner = (Spinner) view
				.findViewById(R.id.sp_add_person_company);
		companySpinner.setAdapter(new MySpinerAdapter(SpinnerTransFormer
				.getcompanySpinerItemInfos(MyDB.getInstance(context)
						.getAllCompanies()), context));

		companySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

				tempId = (int) id;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				tempId = 0;

			}
		});

		builder.setView(view);
		// 确定键
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 不允许姓名为空
				if (nameEditText.getText().toString().trim().length() > 0) {
					MyDB.getInstance(context).insertPerson(
							new Person(
									nameEditText.getText().toString().trim(),
									tempId, positionEditText.getText()
											.toString().trim(), noteEditText
											.getText().toString().trim(),
									emailEditText.getText().toString().trim(),
									phoneEditText.getText().toString().trim(),
									mobilePhoneEditText.getText().toString()
											.trim()).getContValues());
				} else {
					Toast.makeText(context, "请检查输入", Toast.LENGTH_LONG).show();
				}
			}
		});
		// 关闭对话框
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		dialog = builder.create();
		dialog.show();

	}

	public void addCompanyDialog(String... strings) {
		// Event event = new Event();
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		View view = View.inflate(context, R.layout.add_company_dialog, null);
		final EditText nameEditText = (EditText) view
				.findViewById(R.id.et_add_company_name);
		if (strings.length == 1) {
			nameEditText.setText(strings[0]);
		}
		final EditText noteEditText = (EditText) view
				.findViewById(R.id.et_add_company_note);
		builder.setNegativeButton("取消", new AlertDialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

				dialog.cancel();
			}
		});
		builder.setPositiveButton("确定", new AlertDialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

				if (nameEditText.getText().toString().trim().length() > 0) {
					MyDB.getInstance(context).insertCompany(
							new Company(nameEditText.getText().toString()
									.trim(), noteEditText.getText().toString()
									.trim()).getContValues());
				} else {
					Toast.makeText(context, "请输入公司名称", Toast.LENGTH_LONG);
				}
			}
		});
		builder.setView(view);
		this.dialog = builder.create();
		dialog.show();

	}

	public void addEventDialog(String... strings) {
		// 所有人物的链表
		final ArrayList<SpinerItemInfo> selectPersons;
		// 用于所有待选项目的链表
		ArrayList<SpinerItemInfo> selectProject = new ArrayList<SpinerItemInfo>();

		// 初始化两张链表
		MyDB.getInstance(context).load(context);
		selectPersons = SpinnerTransFormer.getPersonSpinerItemInfos(MyDB
				.getInstance(context).getAllPersonInfo());
		selectProject = SpinnerTransFormer.getProjectSpinerItemInfos(MyDB
				.getInstance(context).getAllProjects());

		// 用于存储多选人物简介对象的链表
		final HashMap<Integer, SpinerItemInfo> selectedPersonsHashMap = new HashMap<Integer, SpinerItemInfo>();
		if (strings!=null&&strings.length == 2) {

			SpinerItemInfo info = MyDB.getInstance(context)
					.getPersonInfoByPhone(strings[0]);
			if (info != null) {
				selectedPersonsHashMap.put(info.getId(), info);
			}
		}

		// SpinerItemInfo selectedProject;
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		View view = View.inflate(context, R.layout.add_event_dialog, null);
		final Date date = new Date(System.currentTimeMillis());
		final EditText eventNameEditText = (EditText) view
				.findViewById(R.id.et_add_event_name);
		if (strings.length == 1) {
			eventNameEditText.setText(strings[0]);
		}
		final EditText eventNoteEditText = (EditText) view
				.findViewById(R.id.et_add_event_note);
		if (strings.length == 2) {
			eventNoteEditText.setText(strings[1]);
		}
		final Spinner selectProjectSpinner = (Spinner) view
				.findViewById(R.id.sp_add_event_project);
		final Spinner selectPersonSpinner = (Spinner) view
				.findViewById(R.id.sp_add_event_person);
		final Button addPersonButton = (Button) view
				.findViewById(R.id.bt_add_event_add_person);
		final ListView lvSelectedPeople = (ListView) view
				.findViewById(R.id.lv_add_event_selected_person);
		final DatePicker datePicker = (DatePicker) view
				.findViewById(R.id.datePicker1);
		final TimePicker timePicker = (TimePicker) view
				.findViewById(R.id.timePicker1);
		final Switch isAlarmSwitch = (Switch) view
				.findViewById(R.id.sw_dialog_alarm);
		isAlarmSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				setIschecked(isChecked);
			}
		});
		final MySpinerAdapter listAdapter = new MySpinerAdapter(
				selectedPersonsHashMap, context);
		lvSelectedPeople.setAdapter(listAdapter);
		Calendar calendar = Calendar.getInstance();

		datePicker.init(calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH),
				new OnDateChangedListener() {

					@Override
					public void onDateChanged(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						date.setYear(year);
						date.setMonth(monthOfYear);
						date.setDate(dayOfMonth);

					}
				});
		timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {

			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				date.setHours(hourOfDay);
				date.setMinutes(minute);

			}
		});

		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			/**
			 * 取消的监听
			 */
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		// 为人物spinner添加内容
		selectPersonSpinner.setAdapter(new MySpinerAdapter(selectPersons,
				context));
		// 为添加人物button添加监听
		addPersonButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (selectPersons.size() == 0) {
					Toast.makeText(context, "尚未添加联系人", Toast.LENGTH_SHORT);
					return;
				}
				// 获取Spinner当前选中的item
				SpinerItemInfo selectInfo = (SpinerItemInfo) selectPersonSpinner
						.getSelectedItem();
				// 将item获取的对象添加到哈希表中
				selectedPersonsHashMap.put(Integer.valueOf(selectInfo.getId()),
						selectInfo);
				listAdapter.notifyDataSetChanged();
			}
		});

		// 为选择项目的spinner添加内容
		selectProjectSpinner.setAdapter(new MySpinerAdapter(selectProject,
				context));

		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (eventNameEditText.getText().toString().trim().length() > 0) {
					Event event = new Event(0, eventNameEditText.getText()
							.toString().trim(),
							((SpinerItemInfo) selectProjectSpinner
									.getSelectedItem()).getId(),
							eventNoteEditText.getText().toString().trim(), date
									.getTime(), ischecked);
					int eventId = MyDB.getInstance(context).insertEvent(
							event.getContValues(), selectedPersonsHashMap);
					if (event.isAlarm()) {
						new MyAlarmManager(context).sendAlarm(eventId);
					}
				} else {
					Toast.makeText(context, "empty name", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

		builder.setView(view);
		dialog = builder.create();
		dialog.show();
	}

	public void addProjectDialog(String... strings) {
		Project project;
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		View view = View.inflate(context, R.layout.add_project__dialog, null);
		final EditText projectName = (EditText) view
				.findViewById(R.id.et_add_project_name);
		final EditText projectNote = (EditText) view
				.findViewById(R.id.et_add_project_note);
		if (strings.length == 1) {
			projectName.setText(strings[0]);
		}

		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if (projectName.getText().toString().trim().length() > 0) {

					MyDB.getInstance(context).insertProject(
							new Project(0, projectName.getText().toString()
									.trim(), projectNote.getText().toString()
									.trim()).getContValues());

				}
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});

		builder.setView(view);
		dialog = builder.create();
		dialog.show();

	}

	// 设置是否提醒
	public void setIschecked(boolean ischecked) {
		this.ischecked = ischecked;
	}

	// 修改对话框的入口
	public void modifyDialog(SpinerItemInfo info, Refreshable refreshable) {
		switch (info.getDataType()) {
		case PERSON:
			modifyPerson(info, refreshable);
			break;
		case PROJECT:
			modifyProjectDialog(info, refreshable);

			break;
		case EVENT:
			Log.i("modify", "select");
			modifyEventDialog(info, refreshable);
			break;
		case COMPANY:
			modifyCompanyDialog(info, refreshable);
			break;
		default:
			break;
		}

	}

	// 确认是否删除
	public void confirmDeleteDialog(SpinerItemInfo info, Refreshable refresh) {
		final Refreshable tempRefreshable = refresh;
		final SpinerItemInfo tempInfo = info;
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		TextView textView = new TextView(context);
		textView.setText("确认删除?");
		builder.setView(textView);
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if (MyDB.getInstance(context).remove(tempInfo)) {
					Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT);
					tempRefreshable.refresh(null);
				} else {
					Toast.makeText(context, "删除失败,尚未解除关联.", Toast.LENGTH_SHORT).show();
				}
			}
		});

		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		dialog = builder.create();
		dialog.show();
	}

	// 修改人物信息
	public void modifyPerson(SpinerItemInfo info, Refreshable refreshable) {

		Person person = MyDB.getInstance(context).getPersonById(info.getId());
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		View view = View.inflate(context, R.layout.add_person_dialog, null);

		final SpinerItemInfo tempInfo = info;
		final Refreshable finalRefreshable = refreshable;
		final EditText nameEditText = (EditText) view
				.findViewById(R.id.et_add_person_name);
		nameEditText.setText(person.getName());
		final EditText phoneEditText = (EditText) view
				.findViewById(R.id.et_add_person_phone);
		phoneEditText.setText(person.getPhone());
		final EditText mobilePhoneEditText = (EditText) view
				.findViewById(R.id.et_add_person_mobilePhone);
		mobilePhoneEditText.setText(person.getMobilePhone());
		final EditText emailEditText = (EditText) view
				.findViewById(R.id.et_add_person_email);
		emailEditText.setText(person.getEmail());
		final EditText positionEditText = (EditText) view
				.findViewById(R.id.et_add_person_position);
		positionEditText.setText(person.getPosition());
		final EditText noteEditText = (EditText) view
				.findViewById(R.id.et_add_person_note);
		noteEditText.setText(person.getNote());
		final Spinner companySpinner = (Spinner) view
				.findViewById(R.id.sp_add_person_company);
		companySpinner.setAdapter(new MySpinerAdapter(SpinnerTransFormer
				.getcompanySpinerItemInfos(MyDB.getInstance(context)
						.getAllCompanies()), context));

		companySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

				tempId = (int) id;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				tempId = 0;

			}
		});

		builder.setView(view);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (nameEditText.getText().toString().trim().length() > 0) {
					int newId = MyDB.getInstance(context).insertPerson(
							new Person(
									nameEditText.getText().toString().trim(),
									tempId, positionEditText.getText()
											.toString().trim(), noteEditText
											.getText().toString().trim(),
									emailEditText.getText().toString().trim(),
									phoneEditText.getText().toString().trim(),
									mobilePhoneEditText.getText().toString()
											.trim()).getContValues());
					MyDB.getInstance(context).remove(tempInfo);
					final SpinerItemInfo newInfo = new SpinerItemInfo("",
							newId, DataType.PERSON);
					finalRefreshable.refresh(newInfo);
				} else {
					Toast.makeText(context, "请检查输入", Toast.LENGTH_LONG).show();
				}
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		dialog = builder.create();
		dialog.show();
	}

	// 修改项目信息
	public void modifyProjectDialog(final SpinerItemInfo info,
			final Refreshable refreshable) {

		final Project project = MyDB.getInstance(context).getProjectById(
				info.getId());

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		View view = View.inflate(context, R.layout.add_project__dialog, null);
		final EditText projectName = (EditText) view
				.findViewById(R.id.et_add_project_name);
		final EditText projectNote = (EditText) view
				.findViewById(R.id.et_add_project_note);

		projectName.setText(project.getName());
		projectNote.setText(project.getNote());

		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if (projectName.getText().toString().trim().length() > 0) {
					project.setName(projectName.getText().toString());
					project.setNote(projectNote.getText().toString());
					MyDB.getInstance(context).modifyProject(project);
					final SpinerItemInfo newInfo = new SpinerItemInfo("",
							project.getId(), DataType.PROJECT);
					refreshable.refresh(newInfo);
				}
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});

		builder.setView(view);
		dialog = builder.create();
		dialog.show();

	}

	// 修改事件信息
	public void modifyEventDialog(final SpinerItemInfo info,
			final Refreshable refreshable) {
		// 所有人物的链表
		Log.i("modify", "event");
		final Event event = MyDB.getInstance(context)
				.getEventById(info.getId());
		ArrayList<SpinerItemInfo> selectPersons = new ArrayList<SpinerItemInfo>();
		// 用于所有待选项目的链表
		ArrayList<SpinerItemInfo> selectProject = new ArrayList<SpinerItemInfo>();
		ArrayList<Person> dbPersons = MyDB.getInstance(context)
				.getPersonsByEventId(info.getId());
		// 初始化两张链表
		MyDB.getInstance(context).load(context);
		selectPersons = SpinnerTransFormer.getPersonSpinerItemInfos(MyDB
				.getInstance(context).getAllPersonInfo());
		selectProject = SpinnerTransFormer.getProjectSpinerItemInfos(MyDB
				.getInstance(context).getAllProjects());

		// 用于存储多选人物简介对象的链表
		final HashMap<Integer, SpinerItemInfo> selectedPersonsHashMap = new HashMap<Integer, SpinerItemInfo>();
		for (Person person : dbPersons) {
			// 将原有人物信息添加到已选链表数据源中
			selectedPersonsHashMap.put(person.getId(), new SpinerItemInfo(
					person.getName(), person.getId(), DataType.PERSON));

		}
		// 新建builder对象
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		// 初始化视图
		View view = View.inflate(context, R.layout.add_event_dialog, null);
		final Date date = new Date();
		final EditText eventNameEditText = (EditText) view
				.findViewById(R.id.et_add_event_name);
		eventNameEditText.setText(event.getName());
		final EditText eventNoteEditText = (EditText) view
				.findViewById(R.id.et_add_event_note);
		eventNoteEditText.setText(event.getNote());
		final Spinner selectProjectSpinner = (Spinner) view
				.findViewById(R.id.sp_add_event_project);
		final Spinner selectPersonSpinner = (Spinner) view
				.findViewById(R.id.sp_add_event_person);
		final Button addPersonButton = (Button) view
				.findViewById(R.id.bt_add_event_add_person);
		final ListView lvSelectedPeople = (ListView) view
				.findViewById(R.id.lv_add_event_selected_person);
		final DatePicker datePicker = (DatePicker) view
				.findViewById(R.id.datePicker1);
		final TimePicker timePicker = (TimePicker) view
				.findViewById(R.id.timePicker1);
		final MySpinerAdapter listAdapter = new MySpinerAdapter(
				selectedPersonsHashMap, context);
		lvSelectedPeople.setAdapter(listAdapter);
		lvSelectedPeople.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				selectedPersonsHashMap.remove(Integer.valueOf((int) id));
				listAdapter.notifyDataSetChanged();

			}
		});
		Calendar calendar = Calendar.getInstance();
		datePicker.init(calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH),
				new OnDateChangedListener() {

					@Override
					public void onDateChanged(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						date.setYear(year);
						date.setMonth(monthOfYear);
						date.setDate(dayOfMonth);

					}
				});
		timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {

			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				date.setHours(hourOfDay);
				date.setMinutes(minute);

			}
		});

		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			/**
			 * 取消的监听
			 */
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		// 为人物spinner添加内容
		selectPersonSpinner.setAdapter(new MySpinerAdapter(selectPersons,
				context));
		// 为添加人物button添加监听
		addPersonButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 获取Spinner当前选中的item
				SpinerItemInfo selectInfo = (SpinerItemInfo) selectPersonSpinner
						.getSelectedItem();
				// 将item获取的对象添加到哈希表中
				selectedPersonsHashMap.put(Integer.valueOf(selectInfo.getId()),
						selectInfo);
				listAdapter.notifyDataSetChanged();
			}
		});

		// 为项目spinner添加内容
		selectProjectSpinner.setAdapter(new MySpinerAdapter(selectProject,
				context));

		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (eventNameEditText.getText().toString().trim().length() > 0) {
					MyDB.getInstance(context)
							.removeIndexByEventId(info.getId());
					MyDB.getInstance(context)
							.modifyEvent(
									new Event(
											event.getId(),
											eventNameEditText.getText()
													.toString().trim(),
											((SpinerItemInfo) selectProjectSpinner
													.getSelectedItem()).getId(),
											eventNoteEditText.getText()
													.toString().trim(), date
													.getTime(), ischecked));
					MyDB.getInstance(context).insertIndexes(info.getId(),
							selectedPersonsHashMap);
					MyAlarmManager mam = new MyAlarmManager(context);
					if(event.isAlarm()){
						mam.cancelAlarm(event.getId());
						if(ischecked){
							mam.sendAlarm(event.getId());
						}
					}
					refreshable.refresh(new SpinerItemInfo("", info.getId(),
							DataType.EVENT));
				} else {
					Toast.makeText(context, "empty name", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

		builder.setView(view);
		dialog = builder.create();
		dialog.show();
	}

	/**
	 * 修改公司信息
	 * 
	 * @param info
	 * @param refreshable
	 */
	public void modifyCompanyDialog(final SpinerItemInfo info,
			final Refreshable refreshable) {
		Company company = MyDB.getInstance(context)
				.getCompanyById(info.getId());
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		View view = View.inflate(context, R.layout.add_company_dialog, null);
		final EditText nameEditText = (EditText) view
				.findViewById(R.id.et_add_company_name);
		nameEditText.setText(company.getName());
		final EditText noteEditText = (EditText) view
				.findViewById(R.id.et_add_company_note);
		noteEditText.setText(company.getNote());
		builder.setNegativeButton("取消", new AlertDialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}
		});
		builder.setPositiveButton("确定", new AlertDialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

				if (nameEditText.getText().toString().trim().length() > 0) {
					MyDB.getInstance(context).modifyCompany(
							new Company(info.getId(), nameEditText.getText()
									.toString().trim(), noteEditText.getText()
									.toString().trim()));
					refreshable.refresh(new SpinerItemInfo("", info.getId(),
							DataType.COMPANY));

				} else {
					Toast.makeText(context, "请输入公司名称", Toast.LENGTH_LONG);
				}
			}
		});
		builder.setView(view);
		this.dialog = builder.create();
		dialog.show();

	}

	public static void dialOrMsgdialog(Context context, String mobilePhoneNumber) {
		final Context tempContext = context;
		final String tempMobilePhoneNumber = mobilePhoneNumber.trim();
		AlertDialog.Builder builder = new Builder(context);
		builder.setPositiveButton("电话", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Jumper.dial(tempContext, tempMobilePhoneNumber);
			}
		});
		builder.setNegativeButton("短信", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Jumper.message(tempContext, tempMobilePhoneNumber);
			}
		});
		builder.create().show();
	}
	


}
