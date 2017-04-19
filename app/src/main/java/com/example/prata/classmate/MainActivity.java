package com.example.prata.classmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.prata.classmate.sugarrecord.AddClassActivity;
import com.example.prata.classmate.sugarrecord.TimeTable;
import com.orm.SugarContext;

public class MainActivity extends AppCompatActivity {
	
	private TextView mTextMessage;
	
	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
			= new BottomNavigationView.OnNavigationItemSelectedListener() {
		
		@Override
		public boolean onNavigationItemSelected(@NonNull MenuItem item) {
			switch (item.getItemId()) {
				case R.id.navigation_day1:
					mTextMessage.setText(R.string.title_day1);
					return true;
				case R.id.navigation_day2:
					mTextMessage.setText(R.string.title_day2);
					return true;
				case R.id.navigation_day3:
					mTextMessage.setText(R.string.title_day3);
					return true;
				case R.id.navigation_day4:
					mTextMessage.setText(R.string.title_day4);
					return true;
				case R.id.navigation_day5:
					mTextMessage.setText(R.string.title_day5);
					return true;
			}
			return false;
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		SugarContext.init(this);
		
		mTextMessage = (TextView) findViewById(R.id.message);
		BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
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
