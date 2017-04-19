package com.example.prata.classmate.litho;

import android.text.Layout;

import com.facebook.litho.Column;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.widget.Text;
import com.facebook.yoga.YogaAlign;
import com.facebook.yoga.YogaEdge;

import static com.facebook.yoga.YogaEdge.ALL;

@LayoutSpec
public class ListItemSpec {
	
	@OnCreateLayout
	static ComponentLayout onCreateLayout(
			ComponentContext c,
			@Prop int color,
			@Prop String subjectCode,
			@Prop String timeRange) {
		
		
		return Column.create(c)
				.paddingDip(ALL, 16)
				.alignContent(YogaAlign.CENTER)
				.backgroundColor(color)
				.marginDip(YogaEdge.fromInt(8), 5)
				.child(
						Text.create(c)
								.text(subjectCode)
								.textSizeSp(40)
								.textAlignment(Layout.Alignment.ALIGN_CENTER))
				.child(
						Text.create(c)
								.text(timeRange)
								.textSizeSp(20)
								.textAlignment(Layout.Alignment.ALIGN_CENTER))
				.build();
	}
}