package nz.co.engagenz.copingkete;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;


import nz.co.engagenz.copingkete.database.KetesDbAdapter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;



import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	private ActionBar actionBar;
	
	static final String URL = "http://www.engagenz.co.nz/copingkete/?feed=rss2";// xml目的地址,打开地址看一�?
	// XML 节点
	static final String KEY_ITEM = "item"; //parent node
	static final String KEY_ID = "id";//none
	static final String KEY_TITLE = "title";
	static final String KEY_CREATOR = "dc:creator";
	static final String KEY_DATE = "pubDate";
	static final String KEY_THUMB_URL = "encoded";
	static final String KEY_LINK = "link";
	
	static ListView list;
	LazyAdapter adapter;
	static ArrayList<HashMap<String, String>> itemsList = new ArrayList<HashMap<String, String>>();
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.title));
		setContentView(R.layout.activity_main);

		Thread parseXML = new Thread(new ParseXML());
		parseXML.start();
		
		adapter = new LazyAdapter(this, itemsList);
		// 为单�?��表行添加单击事件
		
		
		

		
		
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {
//		Fragment fragment1 = new Fragment1();
//		Fragment fragment2 = new Fragment1();
//		Fragment fragment3 = new Fragment1();
		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}
		
		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			
			
			Log.i("tag", position+"");
			switch(position){
			case 1:
				Fragment fragment1 = new Fragment2();
				Bundle args1 = new Bundle();  
		        args1.putString("database", "testkete");  
		        //给Fragment初始化参�? 
		        fragment1.setArguments(args1);
				return fragment1;
			case 0:
				Fragment fragment2 = new Fragment1();
				return fragment2;
			case 2:
				Fragment fragment3 = new Fragment2();
				Bundle args3 = new Bundle();  
		        args3.putString("database", "personalkete");  
		        //给Fragment初始化参�? 
		        fragment3.setArguments(args3);
				return fragment3;
			default:
				return null;
			}
			
//			return fragment1;
			
//			Bundle args = new Bundle();
//			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
//			fragment.setArguments(args);
			
			
//			Fragment fragment = new DummySectionFragment();
//			Bundle args = new Bundle();
//			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
//			fragment.setArguments(args);
//			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	@SuppressLint("ValidFragment")
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";
		public DummySectionFragment() {
		}
		

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main_dummy,
					container, false);
//			TextView dummyTextView = (TextView) rootView
//					.findViewById(R.id.section_label);
//			dummyTextView.setText(Integer.toString(getArguments().getInt(
//					ARG_SECTION_NUMBER)));
			list = (ListView) rootView.findViewById(R.id.listView);
			list.setOnItemLongClickListener(new OnItemLongClickListener() {
		        @Override
		        public boolean onItemLongClick(AdapterView<?> parent, View view,
		                int position, long id) {
//		            Toast.makeText(parent.getContext(), "Item "+position+"has been add", Toast.LENGTH_SHORT).show();
		        	Dialog dialog = new AlertDialog.Builder(getActivity())
//                    .setTitle("提示")
                    .setMessage("Add this strategy into:")
                    //set Positive Button in the dialog
                    .setPositiveButton("My Test Kit", 
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int whichButton)
                            {
//                                try {
//                                    Intent intent = new Intent(Intent.ACTION_CALL);
//                                    if(!temp.startsWith(WebView.SCHEME_TEL+"+"))//没有+
//                                    {
//                                    intent.setData(Uri.parse(temp.replace("tel:", "tel:+")));
//                                    }
//                                    else //如果已经�?
//                                    {intent.setData(Uri.parse(temp));}
//                                    startActivity(intent);
//                                } catch (android.content.ActivityNotFoundException e) {
//                                    LOG.e(TAG, "Error dialing " + temp + ": " + e.toString());
//                                }
                                onResume();
                            }
                        })
                     //set Negative Button in the dialog
                     .setNegativeButton("My Personal Kit", 
                        new DialogInterface.OnClickListener() 
                        {
                            public void onClick(DialogInterface dialog, int whichButton)
                            {
                                dialog.dismiss();
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
					
					 Uri uri = Uri.parse(itemsList.get(position).get(KEY_LINK));   
					  Intent intent   = new Intent(Intent.ACTION_VIEW,uri);
					//加下面这句话就是启动系统自带的浏览器打开上面的网�?��  不加下面�?��话，  如果你有多个浏览器，就会弹出让你选择某一浏览器， 然后改浏览器就会打开该网�?..............
//					intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");  
					 startActivity(intent);
				}
				
				
			});
			return rootView;
		}
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
			
//			list.setAdapter(adapter);
			super.handleMessage(msg);
		}
	};
	
	class ParseXML implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			

			XMLParser parser = new XMLParser();
			String xml = parser.getXmlFromUrl(URL); // 从网络获取xml
			Document doc = parser.getDomElement(xml); // 获取 DOM 节点

			NodeList nl = doc.getElementsByTagName(KEY_ITEM);
			// 循环遍历�?��的歌节点 <song>
			for (int i = 0; i < nl.getLength(); i++) {
				// 新建�?�� HashMap
				HashMap<String, String> map = new HashMap<String, String>();
				Element e = (Element) nl.item(i);
				// 每个子节点添加到HashMap关键= >�?
				map.put(KEY_ID, parser.getValue(e, KEY_ID));
				map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));
				map.put(KEY_CREATOR, parser.getValue(e, KEY_CREATOR));
				map.put(KEY_DATE, parser.getValue(e, KEY_DATE));
//				map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL).);
				map.put(KEY_LINK, parser.getValue(e, KEY_LINK));
				Log.i("Thumb_url", parser.getValue(e, "encoded"));

				// HashList添加到数组列�?
				itemsList.add(map);
			}
			Message msg = new Message();
			handler.sendMessage(msg);
		}
		
	}

}
