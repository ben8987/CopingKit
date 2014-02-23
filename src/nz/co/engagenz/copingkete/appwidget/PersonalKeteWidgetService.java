package nz.co.engagenz.copingkete.appwidget;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


public class PersonalKeteWidgetService extends Service {
	
	private static final String TAG="ExampleAppWidgetService"; 

	// ���� widget �Ĺ㲥��Ӧ��action
	private final String ACTION_UPDATE_ALL = "nz.co.engagenz.copingkete.appwidget.UPDATE_ALL";
	// �����Ը��� widget ������
	private static final int UPDATE_TIME = 5000;
	// �����Ը��� widget ���߳�
	private UpdateThread mUpdateThread;
	private Context mContext;
	// �������ڵļ���
	private int count=0;  	

	@Override
	public void onCreate() {
		
		// �����������߳�UpdateThread
		mUpdateThread = new UpdateThread();
		mUpdateThread.start();
		
		mContext = this.getApplicationContext();

		super.onCreate();
	}
	
	@Override
	public void onDestroy(){
		// �ж��̣߳��������̡߳�
        if (mUpdateThread != null) {
        	mUpdateThread.interrupt();
        }
        
		super.onDestroy();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	/*
	 * ����ʼʱ��������startService()ʱ��onStartCommand()��ִ�С�
	 * onStartCommand() �������Ҫ���ã�
	 * (01) �� appWidgetIds ��ӵ�����sAppWidgetIds��
	 * (02) �����߳�
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStartCommand");		
		super.onStartCommand(intent, flags, startId);
		
	    return START_STICKY;
	}
	
    private class UpdateThread extends Thread {

        @Override
        public void run() {
            super.run();

            count = 0;
//	            while (true) {
//	            	Log.d(TAG, "run ... count:"+count);
//	            	count++;
//
//	        		Intent updateIntent=new Intent(ACTION_UPDATE_ALL);
//	        		mContext.sendBroadcast(updateIntent);
//	        		
//	                Thread.sleep(UPDATE_TIME);
//	            } 
			Intent updateIntent=new Intent(ACTION_UPDATE_ALL);
			mContext.sendBroadcast(updateIntent);
        }
    }
}
