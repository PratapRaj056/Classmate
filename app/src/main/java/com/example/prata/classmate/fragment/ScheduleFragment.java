package com.example.prata.classmate.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.OrientationHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prata.classmate.R;
import com.example.prata.classmate.litho.ListItem;
import com.example.prata.classmate.sugarrecord.TimeTable;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentInfo;
import com.facebook.litho.LithoView;
import com.facebook.litho.widget.LinearLayoutInfo;
import com.facebook.litho.widget.Recycler;
import com.facebook.litho.widget.RecyclerBinder;
import com.facebook.soloader.SoLoader;
import com.orm.SugarRecord;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	
	// TODO: Rename and change types of parameters
	private int mParam1;
	private String mParam2;
	
	
	public ScheduleFragment() {
		// Required empty public constructor
	}
	
	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment ScheduleFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static ScheduleFragment newInstance(int param1, String param2) {
		ScheduleFragment fragment = new ScheduleFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getInt(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_schedule, container, false);
		SoLoader.init(getActivity(), false);
		
		
		final ComponentContext context = new ComponentContext(getActivity());
		
		final RecyclerBinder recyclerBinder = new RecyclerBinder(
				context,
				new LinearLayoutInfo(getActivity(), OrientationHelper.VERTICAL, false));
		
		final Component component = Recycler.create(context)
				.binder(recyclerBinder)
				.build();
		
		addContent(recyclerBinder, context);
		
		
		return LithoView.create(context, component);
	}
	
	private void addContent(
			RecyclerBinder recyclerBinder,
			ComponentContext context) {
		TimeTable timeTable = SugarRecord.findById(TimeTable.class, mParam1);
		String subjectCode="", timeRange="";
		int z = 0;
		for (int i = 0; i < 10; i++) {
			switch (i){
				case 0:
					subjectCode = timeTable.getP1();
					timeRange = "8:00–8:50";
					break;
				case 1:
					subjectCode = timeTable.getP2();
					timeRange = "8:50–9:40";
					break;
				case 2:
					subjectCode = timeTable.getP3();
					timeRange = "9:45–10:35";
					break;
				case 3:
					subjectCode = timeTable.getP4();
					timeRange = "10:40–11:30";
					break;
				case 4:
					subjectCode = timeTable.getP5();
					timeRange = "11:35–12:25";
					break;
				case 5:
					subjectCode = timeTable.getP6();
					timeRange = "12:25–01:25";
					break;
				case 6:
					subjectCode = timeTable.getP7();
					timeRange = "01:25–02:15";
					break;
				case 7:
					subjectCode = timeTable.getP8();
					timeRange = "02:20–03:10";
					break;
				case 8:
					subjectCode = timeTable.getP9();
					timeRange = "03:15–04:05";
					break;
				case 9:
					subjectCode = timeTable.getP10();
					timeRange = "04:05–04:55";
					break;
			}
			if(!subjectCode.equals("na")){
				ComponentInfo.Builder componentInfoBuilder = ComponentInfo.create();
				componentInfoBuilder.component(
						ListItem.create(context)
								.color(z % 2 == 0 ? Color.WHITE : Color.LTGRAY)
								.subjectCode(subjectCode)
								.timeRange(timeRange)
								.build());
				recyclerBinder.insertItemAt(z, componentInfoBuilder.build());
				z++;
			}
		}
		
		
		
	}
	
}
