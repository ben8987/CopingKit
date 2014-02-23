package nz.co.engagenz.copingkete;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import nz.co.engagenz.copingkete.Fragment1.ParseXML;
import nz.co.engagenz.copingkete.alert.MyReceiver;
import nz.co.engagenz.copingkete.database.KetesDbAdapter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ToggleButton;


public class Fragment2 extends Fragment {
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	private ListView list;
	private LazyAdapter adapter;
	private ArrayList<HashMap<String, String>> itemsList = new ArrayList<HashMap<String, String>>();
	private KetesDbAdapter dbAdapter;
	private String database;
	
	
	
//	public static final String ARG_SECTION_NUMBER = "section_number";
	public Fragment2() {
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Log.i("tag", "onCreateView");
		database = getArguments().getString("database");
		dbAdapter = new KetesDbAdapter(getActivity());
		dbAdapter.open();
		itemsList = dbAdapter.getAllRecordsAsArrayList(database);
		adapter = new LazyAdapter(inflater,getActivity(), itemsList);
		
		//because the adapter is recreated everytime the view is recreated, so need to rebind the new adapter to the list. 
//		Message msg = new Message();
//		handler.sendMessage(msg);
		
		
		View rootView = inflater.inflate(R.layout.fragment2,
				container, false);
		list = (ListView) rootView.findViewById(R.id.listView);
		list.setAdapter(adapter);
		list.setOnItemLongClickListener(new OnItemLongClickListener() {
	        @Override
	        public boolean onItemLongClick(AdapterView<?> parent, View view,
	                final int position, long id) {
//	            Toast.makeText(parent.getContext(), "Item "+position+"has been add", Toast.LENGTH_SHORT).show();
	        	Dialog dialog = new AlertDialog.Builder(getActivity())
//                .setTitle("閹绘劗锟?)
                .setMessage("Add this strategy into:")
                //set Positive Button in the dialog
                .setPositiveButton("My Reminder", 
                    new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int whichButton)
                        {
                        	LayoutInflater factory = LayoutInflater.from(getActivity());
                            final View reminderSetting = factory.inflate(R.layout.dialog_reminder_setting, null);
                            new AlertDialog.Builder(getActivity())
//                            .setIcon(R.drawable.alarm_dialog)
                            .setTitle(itemsList.get(position).get(MainActivity.KEY_TITLE))
                            .setView(reminderSetting)
                            .setPositiveButton("Activate", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
//                                	if(isOpenInt1){
//                	                	isOpentime1 = "开";
                                	EditText te = (EditText)reminderSetting.findViewById(R.id.search_text);
                                	TimePicker tp = (TimePicker)reminderSetting.findViewById(R.id.timePicker1);
                                	CheckBox cb = (CheckBox)reminderSetting.findViewById(R.id.checkBox1);
                                	ToggleButton toggleSun = (ToggleButton)reminderSetting.findViewById(R.id.toggleButton1);
                                	ToggleButton toggleMon = (ToggleButton)reminderSetting.findViewById(R.id.toggleButton2);
                                	ToggleButton toggleTue = (ToggleButton)reminderSetting.findViewById(R.id.toggleButton3);
                                	ToggleButton toggleWed = (ToggleButton)reminderSetting.findViewById(R.id.toggleButton4);
                                	ToggleButton toggleThu = (ToggleButton)reminderSetting.findViewById(R.id.toggleButton5);
                                	ToggleButton toggleFri = (ToggleButton)reminderSetting.findViewById(R.id.toggleButton6);
                                	ToggleButton toggleSat = (ToggleButton)reminderSetting.findViewById(R.id.toggleButton7);
                                	String description = te.getText().toString();
                                	Calendar reminderTime = Calendar.getInstance();
                                	reminderTime = Calendar.getInstance();
                                	reminderTime.set(Calendar.HOUR_OF_DAY, tp.getCurrentHour());
                                	reminderTime.set(Calendar.MINUTE, tp.getCurrentMinute());
                                	reminderTime.set(Calendar.SECOND, 0);
                                	reminderTime.set(Calendar.MILLISECOND, 0);
                	                	Intent intent = new Intent(getActivity(), MyReceiver.class);
                	                	intent.putExtra("title", itemsList.get(position).get(MainActivity.KEY_TITLE));
                	                	intent.putExtra("link", itemsList.get(position).get(MainActivity.KEY_LINK));
                	                	intent.putExtra("description", description);
                	                	PendingIntent sender=PendingIntent.getBroadcast(
                	                			getActivity(),0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                	                	AlarmManager am;
                	                	getActivity();
										am = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
//                	                	int nowDay = Constants.getNowWeek();
//                	                	int setDay = 0;
//                	                	if(repeatString1.equals("目前无设置"))
//                	                	{
//                	                		if(Constants.differSetTimeAndNowTime(c.getTimeInMillis())){
										if(cb.isChecked()){
											am.setRepeating(AlarmManager.RTC_WAKEUP, reminderTime.getTimeInMillis(), 1000*60*60*24, sender);
										}else{
											am.set(AlarmManager.RTC_WAKEUP, reminderTime.getTimeInMillis(), sender);
										}
                                		
//                                			}
//                	                		else{
//                	                			Toast.makeText(context, R.string.not_time_right, Toast.LENGTH_SHORT);
//                	                		}
//                	                	}
//                	                	if(!(repeatString1.equals("目前无设置"))){
//                	                		String[] setStr = repeatString1.split(",");
//                	                		int[] dayOfNum = Constants.getDayOfNum(setStr);
//                	                		setDay = Constants.getResultDifferDay(dayOfNum, nowDay);
//                	                		int differDay = Constants.compareDayNowToNext(nowDay, setDay);
//                	                		if(differDay == 0){
//                	                			if(Constants.differSetTimeAndNowTime(c.getTimeInMillis())){
//                	                				am.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),sender);
//                	                			}else{
//                	                				am.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis() + Constants.getDifferMillis(7),sender);
//                	                			}
//                	                		}else{
//                	                			am.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis() + Constants.getDifferMillis(differDay),sender);  
//                	                		}
//                	                	}
//                	                }else{
//                	                	isOpentime1 = "关";
//                	                	Intent intent = new Intent(context, CallAlarm.class);
//                	                    PendingIntent sender=PendingIntent.getBroadcast(
//                	                                           context,0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//                	                    AlarmManager am;
//                	                    am =(AlarmManager)activity.getSystemService(context.ALARM_SERVICE);
//                	                    am.cancel(sender);
//                	                    Toast.makeText(context,R.string.alarm_delete1,
//                	                                   Toast.LENGTH_SHORT).show();
//                	                }
//                                    String[] temStr = new String[7];
//                                    temStr[0] = tmpS1;
//                                    temStr[1] = repeatString1;
//                	                temStr[2] = isOpentime1;
//                	                dbHelper.updateAlarmColock(1+"", temStr);
//                	                refreshDBHelper();
                                	
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
//                                	refreshDBHelper();
                                }
                            })
                            .create().show();
                            
                            
//                            String title = songsList.get(position).get(MainActivity.KEY_TITLE);
//                            String date = songsList.get(position).get(MainActivity.KEY_DATE);
//                            String link = songsList.get(position).get(MainActivity.KEY_LINK);
//                            String img = songsList.get(position).get(MainActivity.KEY_THUMB_URL);
//                            Toast.makeText(getActivity(), dbAdapter.insertRecord(title, date, link, "no image", "testkete")+title+date+link+img,Toast.LENGTH_SHORT).show();
                        	
                            onResume();
                        }
                    })
                 //set Negative Button in the dialog
                 .setNegativeButton("My Personal Kit", 
                    new DialogInterface.OnClickListener() 
                    {
                        public void onClick(DialogInterface dialog, int whichButton)
                        {
                        	String title = itemsList.get(position).get(MainActivity.KEY_TITLE);
                            String date = itemsList.get(position).get(MainActivity.KEY_DATE);
                            String link = itemsList.get(position).get(MainActivity.KEY_LINK);
                            String img = itemsList.get(position).get(MainActivity.KEY_THUMB_URL);
                            dbAdapter.insertRecord(title, date, link, "no image", "personalkete");
//                            Toast.makeText(getActivity(), dbAdapter.insertRecord(title, date, link, "no image", "personalkete")+title+date+link+img,Toast.LENGTH_SHORT).show();
                            onResume();
                        }
                    })
                .create();
                dialog.show();
	            return true;
	        }
	    });
		
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				 Uri uri = Uri.parse(itemsList.get(position).get(MainActivity.KEY_LINK));   
				  Intent intent   = new Intent(Intent.ACTION_VIEW,uri);
//				intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");  
				 startActivity(intent);
			}
			
			
		});
		return rootView;
	}
	
//	Handler handler = new Handler() {
//		public void handleMessage(Message msg) {
//			// TODO Auto-generated method stub
////			if (msg.arg1 == 1) {
////				mSelfListView.setAdapter(new ListViewAdapter(titles, urls, likes, ids, names));
////			} else {
////				commentListView.setAdapter(new CommentListViewAdapter(namesForCommentList,dates,comments));
////			}
//			// }
//			
//			list.setAdapter(adapter);
//			super.handleMessage(msg);
//		}
//	};



	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		if(isVisibleToUser){
			itemsList = dbAdapter.getAllRecordsAsArrayList(database);
			adapter.refresh(itemsList);
			Log.i("fragment", "updated");
		}
	}
	
	
	
	
}
