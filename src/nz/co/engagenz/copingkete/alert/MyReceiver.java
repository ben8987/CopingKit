package nz.co.engagenz.copingkete.alert;

import nz.co.engagenz.copingkete.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class MyReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
//		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
//        intent.setClass(context, MyReminder.class);
//        context.startActivity(intent);
		sendNotification(context, "Copingkit Reminder","Click to view the strategy",intent.getStringExtra("link"),intent.getStringExtra("description"));
	}
	
	private void sendNotification(Context ctx, String title,String content, String link, String description) 
    {
        //Get the notification manager 
        String ns = Context.NOTIFICATION_SERVICE; 
        NotificationManager nm =  (NotificationManager)ctx.getSystemService(ns); 
         
        //Create Notification Object 
        int icon = R.drawable.kete;
        CharSequence tickerText = "Copingkit Reminder: "+content; 
        long when = System.currentTimeMillis(); 
         
        Notification notification = new Notification(icon, tickerText, when); 
       
        
 
        //Set ContentView using setLatestEvenInfo
        Intent intent = new Intent(Intent.ACTION_VIEW); 
        intent.setData(Uri.parse(link)); 
        PendingIntent pi = PendingIntent.getActivity(ctx, 0, intent, 0); 
        notification.setLatestEventInfo(ctx, title, content, pi);
               
        //Send notification 
        //The first argument is a unique id for this notification. 
        //This id allows you to cancel the notification later  
        nm.notify(1, notification); 
    }

}
