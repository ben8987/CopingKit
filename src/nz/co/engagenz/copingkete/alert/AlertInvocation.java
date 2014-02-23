//package nz.co.engagenz.copingkete.alert;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//
//
//public class AlertInvocation extends BroadcastReceiver
//{
//	  @Override
//	  public void onReceive(final Context context, Intent intent)
//	  {	  
//		    String getStr = intent.getExtras().getString("RESULT");
//		    Log.v("wangxianming", "RESULT = "+getStr);
//			Intent alaramIntent = new Intent(context, AlarmAgainSetting.class);
//			Bundle bundleRet = new Bundle();
//			bundleRet.putString("STR_RESULT", getStr);
//			alaramIntent.putExtras(bundleRet);
//			alaramIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			context.startActivity(alaramIntent);
//	  }
//	}
