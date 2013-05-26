package com.whitewatersoftware.temperaturelogger;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Formatter;

public class TemperatureLoggerWidget extends AppWidgetProvider {

    public void onUpdate(Context context,
			 AppWidgetManager appWidgetManager,
			 int[] appWidgetIds) {
        final int N=appWidgetIds.length;
        for (int i=0; i<N; i++) {
            int appWidgetId=appWidgetIds[i];
	    RemoteViews views=
		new RemoteViews(context.getPackageName(),R.layout.preview);
	    Intent activity=new Intent(context,TemperatureLogger.class);
	    PendingIntent intent=
		PendingIntent.getActivity(context,0,activity,0);
	    views.setOnClickPendingIntent(R.id.preview,intent);
	    appWidgetManager.updateAppWidget(appWidgetId,views);
	}
    }

    public void onReceive(Context context, Intent intent) {
	if (intent.getAction().equals("ACTION_WIDGET_UPDATE_FROM_ACTIVITY")) {
	    String msg=intent.getExtras().getString("INTENT_EXTRA_WIDGET_TEXT");
	    if (msg.equals("finish")) {
		RemoteViews views=
		    new RemoteViews(context.getPackageName(),R.layout.preview);
		Intent activity=new Intent(context,TemperatureLogger.class);
		PendingIntent pintent=
		    PendingIntent.getActivity(context,0,activity,0);
		views.setOnClickPendingIntent(R.id.preview,pintent);
		ComponentName cn=
		    new ComponentName(context,TemperatureLoggerWidget.class);
		AppWidgetManager.getInstance(context).updateAppWidget(cn,views);
	    } else {
		RemoteViews views=
		    new RemoteViews(context.getPackageName(),R.layout.widget);
		Intent activity=new Intent(context,TemperatureLogger.class);
		PendingIntent pintent=
		    PendingIntent.getActivity(context,0,activity,0);
		views.setOnClickPendingIntent(R.id.widget,pintent);
		views.setTextViewText(R.id.temperature,msg);
		ComponentName cn=
		    new ComponentName(context,TemperatureLoggerWidget.class);
		AppWidgetManager.getInstance(context).updateAppWidget(cn,views);
	    }
        } else
	    super.onReceive(context,intent);
    }

}
