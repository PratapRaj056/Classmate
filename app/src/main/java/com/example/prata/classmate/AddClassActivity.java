package com.example.prata.classmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.prata.classmate.sugarrecord.TimeTable;
import com.orm.SugarRecord;

public class AddClassActivity extends AppCompatActivity {

    Spinner dayOrderSpinner, periodSpinner;
	EditText subjectcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        getSupportActionBar().setTitle("Add New Lecture");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	    subjectcode = (EditText) findViewById(R.id.lectureName);
        dayOrderSpinner = (Spinner) findViewById(R.id.dayOrderSpinner);
        periodSpinner = (Spinner) findViewById(R.id.periodSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dayOrder, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dayOrderSpinner.setAdapter(adapter);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.Timing, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        periodSpinner.setAdapter(adapter2);
	    
	    dayOrderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				periodSpinner.setSelection(0);
		    }
		
		    @Override
		    public void onNothingSelected(AdapterView<?> parent) {
			
		    }
	    });
	    periodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			    TimeTable timeTable = SugarRecord.findById(TimeTable.class, (dayOrderSpinner.getSelectedItemPosition()+6));
			    String et="";
			    switch (position){
				    case 0: et = timeTable.getP1();
					    break;
				    case 1: et = timeTable.getP2();
				    	break;
				    case 2: et = timeTable.getP3();
					    break;
				    case 3: et = timeTable.getP4();
					    break;
				    case 4: et = timeTable.getP5();
					    break;
				    case 5: et = timeTable.getP6();
					    break;
				    case 6: et = timeTable.getP7();
					    break;
				    case 7: et = timeTable.getP8();
					    break;
				    case 8: et = timeTable.getP9();
					    break;
				    case 9: et = timeTable.getP10();
					    break;
			    }
			    subjectcode.setText(et);
		    }
		
		    @Override
		    public void onNothingSelected(AdapterView<?> parent) {
			
		    }
	    });
    }
    
    public void onClickLecture(View v){
	    String et = "";
	    if(subjectcode.getText()!=null && subjectcode.getText().equals("")){
		    et = "na";
	    }else{
		    et = subjectcode.getText().toString();
	    }
	    TimeTable timeTable = SugarRecord.findById(TimeTable.class, (dayOrderSpinner.getSelectedItemPosition()+6));
	    switch (periodSpinner.getSelectedItemPosition()){
		    case 0: timeTable.setP1(et);
			    break;
		    case 1: timeTable.setP2(et);
			    break;
		    case 2: timeTable.setP3(et);
			    break;
		    case 3: timeTable.setP4(et);
			    break;
		    case 4: timeTable.setP5(et);
			    break;
		    case 5: timeTable.setP6(et);
			    break;
		    case 6: timeTable.setP7(et);
			    break;
		    case 7: timeTable.setP8(et);
			    break;
		    case 8: timeTable.setP9(et);
			    break;
		    case 9: timeTable.setP10(et);
			    break;
	    }
	    timeTable.update();
	    Toast.makeText(this, "Timetable Updated!", Toast.LENGTH_SHORT).show();
	    
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            	finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		startActivity(new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
		finish();
	}
}
