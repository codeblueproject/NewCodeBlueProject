package com.codeblue.receiver;

import java.util.ArrayList;

import com.codeblue.geolocation.CodeBlueViewLocation;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class CodeBlueSMSReceiver extends BroadcastReceiver {

	private CodeBlueSMSMethods cbMethods;

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		SmsMessage[] msgs = null;
		String str = "";
		if (bundle != null) {
			Object[] pdus = (Object[]) bundle.get("pdus");
			msgs = new SmsMessage[pdus.length];
			for (int i = 0; i < msgs.length; i++) {
				msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				str += msgs[i].getMessageBody().toString();
			}
			Toast.makeText(context, "mao ni"+str, Toast.LENGTH_SHORT).show();
			// createNotification(context);
			cbMethods = new CodeBlueSMSMethods();
			// check if the sent message is from codeblue
			if (cbMethods.isFromCodeBlue(str)) {
				createNotification(context, cbMethods.getSenderLocation(str));
			}
		}
	}

	// called to create notification when a codeblue broadcast is received
	private void createNotification(Context context, ArrayList<Double> location) {
		Intent intent = new Intent(context, CodeBlueViewLocation.class);
		intent.putExtra("senderLatitude", location.get(0));
		intent.putExtra("senderLongitude", location.get(1));
		PendingIntent pIntent = PendingIntent
				.getActivity(context, 0, intent, 0);

		String longText = "A PendingIntent is a token that you give to another application (e.g. Notification Manager, Alarm Manager or other 3rd party applications), which allows this other application to use the permissions of your application to execute a predefined piece of code.To perform a broadcast via a pending intent so get a PendingIntent via PendingIntent.getBroadcast(). To perform an activity via an pending intent you receive the activity via PendingIntent.getActivity().";
/*		Notification noti = new NotificationCompat.Builder(context)
				.setContentTitle("CodeBlue Emergency ")
				.setContentText("Emergency! Emergency!")
				.setSmallIcon(R.drawable.ebutton)
				.setContentIntent(pIntent)
				.setAutoCancel(true)
				.addAction(R.drawable.ebutton, "Call", pIntent)
				.addAction(R.drawable.ebutton, "More", pIntent)
				.addAction(R.drawable.ebutton, "And more", pIntent)
				.setStyle(
						new NotificationCompat.BigTextStyle().bigText(longText))
				.build();
*/
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(context.NOTIFICATION_SERVICE);

//		notificationManager.notify(0, noti);
	}

}