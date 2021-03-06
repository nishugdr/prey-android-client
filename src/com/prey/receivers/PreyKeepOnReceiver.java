package com.prey.receivers;

import com.prey.PreyConfig;

import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;

public class PreyKeepOnReceiver extends BroadcastReceiver {

	public PreyKeepOnReceiver() {
	}

	@TargetApi(Build.VERSION_CODES.ECLAIR_MR1)
	public void onReceive(Context context, Intent intent) {
		boolean keepOn = PreyConfig.getPreyConfig(context).isKeepOn();
		if (keepOn) {
			if ("android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(intent.getAction())) {
				boolean flag = ((KeyguardManager) context.getSystemService("keyguard")).inKeyguardRestrictedInputMode();
				if (flag) {
					PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
					boolean isScreenOn = pm.isScreenOn();
					if (isScreenOn) {
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
						}
						Intent intentClose = new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS");
						context.sendBroadcast(intentClose);
					}
				}
			}
		}
	}

}
