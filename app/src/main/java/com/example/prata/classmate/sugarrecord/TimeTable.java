package com.example.prata.classmate.sugarrecord;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

public class TimeTable extends SugarRecord{
	@Unique
	int id;
	String p1;
	String p2;
	String p3;
	String p4;
	String p5;
	String p6;
	String p7;
	String p8;
	String p9;
	String p10;
	
	public TimeTable(){
		super();
	}
	
	
	public TimeTable(int id, String p1, String p2, String p3, String p4, String p5, String p6, String p7, String p8, String p9, String p10) {
		this.id = id;
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.p4 = p4;
		this.p5 = p5;
		this.p6 = p6;
		this.p7 = p7;
		this.p8 = p8;
		this.p9 = p9;
		this.p10 = p10;
	}
}
