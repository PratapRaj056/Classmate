package com.example.prata.classmate;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.prata.classmate.fragment.ScheduleFragment;
import com.example.prata.classmate.litho.ListItem;
import com.example.prata.classmate.sugarrecord.TimeTable;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentInfo;
import com.facebook.litho.widget.RecyclerBinder;
import com.orm.SugarContext;

public class MainActivity extends AppCompatActivity {
	
	//private TextView mTextMessage;
	private Fragment fragment;
	private FragmentManager fragmentManager;
	
	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
			= new BottomNavigationView.OnNavigationItemSelectedListener() {
		
		@Override
		public boolean onNavigationItemSelected(@NonNull MenuItem item) {
			switch (item.getItemId()) {
				case R.id.navigation_day1:
					//mTextMessage.setText(R.string.title_day1);
					fragment = ScheduleFragment.newInstance("1", "");
					break;
				case R.id.navigation_day2:
					//mTextMessage.setText(R.string.title_day2);
					fragment = ScheduleFragment.newInstance("2", "");
					break;
				case R.id.navigation_day3:
					//mTextMessage.setText(R.string.title_day3);
					fragment = ScheduleFragment.newInstance("3", "");
					break;
				case R.id.navigation_day4:
					//mTextMessage.setText(R.string.title_day4);
					fragment = ScheduleFragment.newInstance("4", "");
					break;
				case R.id.navigation_day5:
					//mTextMessage.setText(R.string.title_day5);
					fragment = ScheduleFragment.newInstance("5", "");
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
		
		fragmentManager = getSupportFragmentManager();
		fragment = ScheduleFragment.newInstance("1", "");
		final FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(R.id.content, fragment).commit();
		
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
		
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
	}
	
	private void addContent(
			RecyclerBinder recyclerBinder,
			ComponentContext context) {
		for (int i = 0; i < 32; i++) {
			ComponentInfo.Builder componentInfoBuilder = ComponentInfo.create();
			componentInfoBuilder.component(
					ListItem.create(context)
							.color(i % 2 == 0 ? Color.WHITE : Color.LTGRAY)
							.title("Hello, world!")
							.subtitle("Litho tutorial")
							.build());
			recyclerBinder.insertItemAt(i, componentInfoBuilder.build());
		}
	}
	
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
            default:
                return super.onOptionsItemSelected(item);
		}
	}
}
