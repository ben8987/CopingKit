package nz.co.engagenz.copingkete.appwidget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;


import nz.co.engagenz.copingkete.MainActivity;
import nz.co.engagenz.copingkete.R;
import nz.co.engagenz.copingkete.database.KetesDbAdapter;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Toast;


public class PersonalKeteWidgetProvider extends AppWidgetProvider {
	private static final String TAG = "ExampleAppWidgetProvider";

	private boolean DEBUG = false;
    // 启动ExampleAppWidgetService服务对应的action
    private final Intent PERSONAL_WIDGET_SERVICE_INTENT = 
    		new Intent("android.appwidget.action.PERSONALKETEWIDGETSERVICE");
    // 更新 widget 的广播对应的action
	private final String ACTION_UPDATE_ALL = "nz.co.engagenz.copingkete.appwidget.UPDATE_ALL";
    // 保存 widget 的id的HashSet，每新建一个 widget 都会为该 widget 分配一个 id。
	private static Set idsSet = new HashSet();
	// 按钮信息
    private static final int BUTTON_REFRESH = 1;
    
    private KetesDbAdapter dbAdapter;
    private static ArrayList<HashMap<String, String>> itemsList = new ArrayList<HashMap<String, String>>();
    private static int count = 1;
    private static int count2 = 1;
    private Animation paperAnimation1 = null;
    
//	// 图片数组
//    private static final int[] ARR_IMAGES = { 
//    	R.drawable.sample_0, 
//    	R.drawable.sample_1, 
//    	R.drawable.sample_2, 
//    	R.drawable.sample_3, 
//    	R.drawable.sample_4, 
//    	R.drawable.sample_5,
//    	R.drawable.sample_6,
//    	R.drawable.sample_7,
//    };
    
	// onUpdate() 在更新 widget 时，被执行，
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		Log.d(TAG, "onUpdate(): appWidgetIds.length="+appWidgetIds.length);

		// 每次 widget 被创建时，对应的将widget的id添加到set中
		for (int appWidgetId : appWidgetIds) {
			idsSet.add(Integer.valueOf(appWidgetId));
		}
		prtSet();
	}
	
//    // 当 widget 被初次添加 或者 当 widget 的大小被改变时，被调用 
//	@Override  
//    public void onAppWidgetOptionsChanged(Context context,  
//            AppWidgetManager appWidgetManager, int appWidgetId,
//            Bundle newOptions) {
//    	Log.d(TAG, "onAppWidgetOptionsChanged");
//        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);  
//    }  
    
    // widget被删除时调用  
    @Override  
    public void onDeleted(Context context, int[] appWidgetIds) {  
		Log.d(TAG, "onDeleted(): appWidgetIds.length="+appWidgetIds.length);

		// 当 widget 被删除时，对应的删除set中保存的widget的id
		for (int appWidgetId : appWidgetIds) {
			idsSet.remove(Integer.valueOf(appWidgetId));
		}
		prtSet();
		
        super.onDeleted(context, appWidgetIds);  
    }

    // 第一个widget被创建时调用  
    @Override  
    public void onEnabled(Context context) {  
    	Log.d(TAG, "onEnabled");
    	// 在第一个 widget 被创建时，开启服务
    	context.startService(PERSONAL_WIDGET_SERVICE_INTENT);
    	
        super.onEnabled(context);
        
    }  
    
    // 最后一个widget被删除时调用  
    @Override  
    public void onDisabled(Context context) {  
    	Log.d(TAG, "onDisabled");

    	// 在最后一个 widget 被删除时，终止服务
    	context.stopService(PERSONAL_WIDGET_SERVICE_INTENT);

        super.onDisabled(context);  
    }
    
    
    // 接收广播的回调函数
    @Override  
    public void onReceive(Context context, Intent intent) {

        final String action = intent.getAction();
        Log.d(TAG, "OnReceive:Action: " + action);
        if (ACTION_UPDATE_ALL.equals(action)) {
        	// “更新”广播
        	updateAllAppWidgets(context, AppWidgetManager.getInstance(context), idsSet);
	    } else if (intent.hasCategory(Intent.CATEGORY_ALTERNATIVE)) {
	    	// “按钮点击”广播
	        Uri data = intent.getData();
	        int buttonId = Integer.parseInt(data.getSchemeSpecificPart());
	        if (buttonId == BUTTON_REFRESH) {
	        	updateAllAppWidgets(context, AppWidgetManager.getInstance(context), idsSet);
	        	

	        }else if(buttonId == 2){
	        	Uri uri = Uri.parse(itemsList.get((count-1)%itemsList.size()).get("link"));   
				  Intent i   = new Intent(Intent.ACTION_VIEW,uri);
				  i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");  
				 context.startActivity(i);
	        }else if (buttonId == 3){
	        	Intent i   = new Intent();
	        	i.setClass(context, MainActivity.class);
				  i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");  
				 context.startActivity(i);
	        }
	    }
        
        super.onReceive(context, intent);  
    }  

    // 更新所有的 widget 
    private void updateAllAppWidgets(Context context, AppWidgetManager appWidgetManager, Set set) {

		Log.d(TAG, "updateAllAppWidgets(): size="+set.size());
		dbAdapter = new KetesDbAdapter(context);
		dbAdapter.open();
		itemsList = dbAdapter.getAllRecordsAsArrayList("personalkete");
		dbAdapter.close();
		// widget 的id
    	int appID;
    	// 迭代器，用于遍历所有保存的widget的id
    	Iterator it = set.iterator();

    	while (it.hasNext()) {
    		appID = ((Integer)it.next()).intValue();    
    		// 获取 example_appwidget.xml 对应的RemoteViews    		
    		RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.widget_personalkete);
    		// 随机获取一张图片
    		
    		if(itemsList.size()>0){
//    			int index = (new java.util.Random().nextInt(itemsList.size()));
    			
    			// 设置显示
//        		remoteView.setTextViewText(R.id.textView, itemsList.get(count%itemsList.size()).get("title"));
    			remoteView.setImageViewBitmap(R.id.textView, buildUpdate(itemsList.get(count%itemsList.size()).get("title"), context));
//        		remoteView.setImageViewResource(R.id.iv_show, ARR_IMAGES[index]);
//        		Toast.makeText(context, "Changed"+itemsList.size()+" "+count, Toast.LENGTH_SHORT).show();
    			if(count2%2==1){
    				remoteView.setViewVisibility(R.id.paper, View.INVISIBLE);
    				count++;
    			}else{
    				remoteView.setViewVisibility(R.id.paper, View.VISIBLE);
    			}
    			count2++;
	        	
    		}
    		
//    		int index = 1;
//    		if (DEBUG) Log.d(TAG, "onUpdate(): index="+index);
    		
    		
    		
    		
    		// 设置点击按钮对应的PendingIntent：即点击按钮时，发送广播。
    		remoteView.setOnClickPendingIntent(R.id.btn_refresh, getPendingIntent(context,
                    BUTTON_REFRESH));
    		remoteView.setOnClickPendingIntent(R.id.textView, getPendingIntent(context,
                    2));
    		remoteView.setOnClickPendingIntent(R.id.btn_kete, getPendingIntent(context,
                    3));

//    		TimerTask task = new TimerTask(){
//        	    public void run(){
//        	    //execute the task
////        	    	RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.widget_personalkete);
//        	    	remoteView.setViewVisibility(R.id.paper, View.INVISIBLE);
//        	    }
//        	};
//        	Timer timer = new Timer();
//        	timer.schedule(task, 1000);
    		
    		
    		// 更新 widget
    		appWidgetManager.updateAppWidget(appID, remoteView);	
    		
    	}
    	
    	
    	
    	
    	
    	
	}

    private PendingIntent getPendingIntent(Context context, int buttonId) {
        Intent intent = new Intent();
        intent.setClass(context, PersonalKeteWidgetProvider.class);
        intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
        intent.setData(Uri.parse("custom:" + buttonId));
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0 );
        return pi;
    }

    // 调试用：遍历set
    private void prtSet() {
    	if (DEBUG) {
	    	int index = 0;
	    	int size = idsSet.size();
	    	Iterator it = idsSet.iterator();
	    	Log.d(TAG, "total:"+size);
	    	while (it.hasNext()) {
	    		Log.d(TAG, index + " -- " + ((Integer)it.next()).intValue());
	    	}
    	}
    }
    
    private Bitmap buildUpdate(String content,Context c) 
    {
        Bitmap myBitmap = Bitmap.createBitmap(600, 160, Bitmap.Config.ARGB_4444);
        Canvas myCanvas = new Canvas(myBitmap);
//        myCanvas.drawRGB(0, 0, 0);
        Paint paint = new Paint();
        Typeface clock = Typeface.createFromAsset(c.getAssets(),"LadylikeBB.ttf");
        paint.setAntiAlias(true);
        paint.setSubpixelText(true);
        paint.setTypeface(clock);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setTextSize(50);
        paint.setTextAlign(Align.LEFT);
        myCanvas.drawText(content, 20, 50, paint);
        return myBitmap;
    }
}
