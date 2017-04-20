package com.example.prata.classmate;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.prata.classmate.fragment.ScheduleFragment;
import com.example.prata.classmate.sugarrecord.TimeTable;
import com.orm.SugarContext;
import com.orm.SugarRecord;

public class MainActivity extends AppCompatActivity {
	
	//private TextView mTextMessage;
	private Fragment fragment;
	private FragmentManager fragmentManager;
	
	TimeTable t;
	
	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
			= new BottomNavigationView.OnNavigationItemSelectedListener() {
		
		@Override
		public boolean onNavigationItemSelected(@NonNull MenuItem item) {
			switch (item.getItemId()) {
				case R.id.navigation_day1:
					//mTextMessage.setText(R.string.title_day1);
					fragment = ScheduleFragment.newInstance(6, "");
					break;
				case R.id.navigation_day2:
					//mTextMessage.setText(R.string.title_day2);
					fragment = ScheduleFragment.newInstance(7, "");
					break;
				case R.id.navigation_day3:
					//mTextMessage.setText(R.string.title_day3);
					fragment = ScheduleFragment.newInstance(8, "");
					break;
				case R.id.navigation_day4:
					//mTextMessage.setText(R.string.title_day4);
					fragment = ScheduleFragment.newInstance(9, "");
					break;
				case R.id.navigation_day5:
					//mTextMessage.setText(R.string.title_day5);
					fragment = ScheduleFragment.newInstance(10, "");
					break;
			}
			final FragmentTransaction transaction = fragmentManager.beginTransaction();
			transaction.replace(R.id.content, fragment).commit();
			return true;
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SugarContext.init(this);
		/*SoLoader.init(this, false);
		
		
		final ComponentContext context = new ComponentContext(this);
		
		final RecyclerBinder recyclerBinder = new RecyclerBinder(
				context,
				new LinearLayoutInfo(this, OrientationHelper.VERTICAL, false));
		
		final Component component = Recycler.create(context)
				.binder(recyclerBinder)
				.build();
		
		addContent(recyclerBinder, context);
		
		setContentView(LithoView.create(context, component));*/
		
		
		//mTextMessage = (TextView) findViewById(R.id.message);
		BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
		
		TimeTable timeTable = new TimeTable(1, "A", "A", "F", "F", "D" ,"na", "na", "na", "na", "na");
		timeTable.save();
		TimeTable timeTable2 = new TimeTable(2, "na", "na", "na", "na", "na" ,"B", "B", "G", "G", "A");
		timeTable2.save();
		TimeTable timeTable3 = new TimeTable(3, "C", "C", "E", "F", "D" ,"na", "na", "na", "na", "na");
		timeTable3.save();
		TimeTable timeTable4 = new TimeTable(4, "na", "na", "na", "na", "na" ,"D", "D", "B", "C", "G");
		timeTable4.save();
		TimeTable timeTable5 = new TimeTable(5, "E", "E", "A", "B", "C" ,"na", "na", "na", "na", "na");
		timeTable5.save();
		
		TimeTable timeTable6 = new TimeTable(6, "na", "SE1017", "SE1023", "SE1023", "na" ,"na", "DE-III", "SE1017/SE1018 Lab", "SE1017/SE1018 Lab", "na");
		timeTable6.save();
		TimeTable timeTable7 = new TimeTable(7, "na", "na", "na", "na", "SE1049" ,"SE1049", "SE1018", "OE-II", "OE-II", "SE1017");
		timeTable7.save();
		TimeTable timeTable8 = new TimeTable(8, "na", "SE1019", "SE1020", "SE1023", "na" ,"na", "DE-III", "na", "na", "na");
		timeTable8.save();
		TimeTable timeTable9 = new TimeTable(9, "na", "na", "SE1017/SE1018 Lab", "SE1017/SE1018 Lab", "na" ,"PD", "PD", "SE1018", "SE1019", "OE-II");
		timeTable9.save();
		TimeTable timeTable10 = new TimeTable(10, "SE1020", "SE1020", "SE1017", "SE1018", "SE1019" ,"DE-III", "na", "na", "na", "na");
		timeTable10.save();
		
		fragmentManager = getSupportFragmentManager();
		fragment = ScheduleFragment.newInstance(6, "");
		final FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(R.id.content, fragment).commit();
		
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
		
		t = SugarRecord.findById(TimeTable.class, 6);
		
	}
	
	/*private void addContent(
			RecyclerBinder recyclerBinder,
			ComponentContext context) {
		for (int i = 0; i < 32; i++) {
			ComponentInfo.Builder componentInfoBuilder = ComponentInfo.create();
			componentInfoBuilder.component(
					ListItem.create(context)
							.color(i % 2 == 0 ? Color.WHITE : Color.LTGRAY)
							.dayOrder(mp)
							.build());
			recyclerBinder.insertItemAt(i, componentInfoBuilder.build());
		}
	}*/
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//return super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.action_scan:
				startActivity(new Intent(this, UploadImageActivity.class));
				return true;
            case R.id.action_add:
                startActivity(new Intent(this, AddClassActivity.class));
                return true;
			case R.id.action_notification:
				sendNotification2(t.getP2(), "8:00-8:50");
				return true;
            default:
                return super.onOptionsItemSelected(item);
		}
	}
	
	private void sendNotification(String title, String messageBody) {
		Intent intent = new Intent(this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
				PendingIntent.FLAG_ONE_SHOT);
		
		Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
				.setSmallIcon(R.mipmap.ic_launcher)
				.setContentTitle(title)
				.setContentText(messageBody)
				.setAutoCancel(true)
				.setSound(defaultSoundUri)
				.setContentIntent(pendingIntent);
		
		NotificationManager notificationManager =
				(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		
		notificationManager.notify(0, notificationBuilder.build());
	}
	static int i = 0;
	private void sendNotification2(final String title, final String messageBody) {
		runOnUiThread(new Thread(new Runnable() {
			@Override
			public void run() {
				sendNotification(title, messageBody);
				i++;
				try {
					Thread.sleep(60000);
					sendNotification2(title, messageBody);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}));
	}
}
