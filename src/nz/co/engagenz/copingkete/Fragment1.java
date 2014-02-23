package nz.co.engagenz.copingkete;

import java.util.ArrayList;
import java.util.HashMap;

import nz.co.engagenz.copingkete.MainActivity.ParseXML;
import nz.co.engagenz.copingkete.database.KetesDbAdapter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Toast;

public class Fragment1 extends Fragment {
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	private ListView list;
	private LazyAdapter adapter;
	private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
	private KetesDbAdapter dbAdapter;
	private EditText searchText;
	private ImageView searchImage;
	
	
	
//	public static final String ARG_SECTION_NUMBER = "section_number";
	public Fragment1() {
		Thread parseXML = new Thread(new ParseXML());
		parseXML.start();
		Log.i("tag", "parsing");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Log.i("tag", "onCreateView");
		adapter = new LazyAdapter(inflater,getActivity(), songsList);
		dbAdapter = new KetesDbAdapter(getActivity());
		dbAdapter.open();
		//because the adapter is recreated everytime the view is recreated, so need to rebind the new adapter to the list. 
		Message msg = new Message();
		handler.sendMessage(msg);
		
		View rootView = inflater.inflate(R.layout.fragment1,
				container, false);
		list = (ListView) rootView.findViewById(R.id.listView);
		list.setOnItemLongClickListener(new OnItemLongClickListener() {
	        @Override
	        public boolean onItemLongClick(AdapterView<?> parent, View view,
	                final int position, long id) {
//	            Toast.makeText(parent.getContext(), "Item "+position+"has been add", Toast.LENGTH_SHORT).show();
	        	Dialog dialog = new AlertDialog.Builder(getActivity())
//                .setTitle("鎻愮�?)
                .setMessage("Add this strategy into:")
                //set Positive Button in the dialog
                .setPositiveButton("My Test Kit", 
                    new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int whichButton)
                        {
                            String title = songsList.get(position).get(MainActivity.KEY_TITLE);
                            String date = songsList.get(position).get(MainActivity.KEY_DATE);
                            String link = songsList.get(position).get(MainActivity.KEY_LINK);
                            String img = songsList.get(position).get(MainActivity.KEY_THUMB_URL);
                            dbAdapter.insertRecord(title, date, link, "no image", "testkete");
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
                        	String title = songsList.get(position).get(MainActivity.KEY_TITLE);
                            String date = songsList.get(position).get(MainActivity.KEY_DATE);
                            String link = songsList.get(position).get(MainActivity.KEY_LINK);
                            String img = songsList.get(position).get(MainActivity.KEY_THUMB_URL);
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
				
				 Uri uri = Uri.parse(songsList.get(position).get(MainActivity.KEY_LINK));   
				  Intent intent   = new Intent(Intent.ACTION_VIEW,uri);
//				intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");  
				 startActivity(intent);
			}
			
			
		});
		
		searchText = (EditText)rootView.findViewById(R.id.search_text);
		searchImage = (ImageView)rootView.findViewById(R.id.search_image);
		searchImage.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Uri uri = Uri.parse("http://www.engagenz.co.nz/copingkete/?s=" + searchText.getText()); 
				searchText.setText(null);
				  Intent intent   = new Intent(Intent.ACTION_VIEW,uri);
//				intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");  
				 startActivity(intent);
				 
			}
			
		});
		
		return rootView;
	}
	
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
//			if (msg.arg1 == 1) {
//				mSelfListView.setAdapter(new ListViewAdapter(titles, urls, likes, ids, names));
//			} else {
//				commentListView.setAdapter(new CommentListViewAdapter(namesForCommentList,dates,comments));
//			}
			// }
			
			list.setAdapter(adapter);
			super.handleMessage(msg);
		}
	};
	
	class ParseXML implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			XMLParser parser = new XMLParser();
			String xml = parser.getXmlFromUrl(MainActivity.URL); // 浠庣綉缁滆幏鍙杧ml
			Document doc = parser.getDomElement(xml); // 鑾峰�?DOM 鑺傜�?

			NodeList nl = doc.getElementsByTagName(MainActivity.KEY_ITEM);
			for (int i = 0; i < nl.getLength(); i++) {
				HashMap<String, String> map = new HashMap<String, String>();
				Element e = (Element) nl.item(i);
				map.put(MainActivity.KEY_ID, parser.getValue(e, MainActivity.KEY_ID));
				map.put(MainActivity.KEY_TITLE, parser.getValue(e, MainActivity.KEY_TITLE));
				map.put(MainActivity.KEY_CREATOR, parser.getValue(e, MainActivity.KEY_CREATOR));
				map.put(MainActivity.KEY_DATE, parser.getValue(e, MainActivity.KEY_DATE));
//				map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL).);
				map.put(MainActivity.KEY_LINK, parser.getValue(e, MainActivity.KEY_LINK));
//				Log.i("Thumb_url", parser.getValue(e, "encoded"));

				songsList.add(map);
			}
			Message msg = new Message();
			handler.sendMessage(msg);
		}
		
	}
	
}