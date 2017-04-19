package com.example.prata.classmate.litho;

import com.facebook.litho.Column;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.widget.Text;
import com.facebook.yoga.YogaAlign;

import static com.facebook.yoga.YogaEdge.ALL;

@LayoutSpec
public class ListItemSpec {
	
	@OnCreateLayout
	static ComponentLayout onCreateLayout(
			ComponentContext c,
			@Prop int color,
			@Prop String title,
			@Prop String subtitle) {
		
		return Column.create(c)
				.paddingDip(ALL, 16)
				.alignContent(YogaAlign.CENTER)
				.backgroundColor(color)
				.child(
						Text.create(c)
								.text(title)
								.textSizeSp(40))
				.child(
						Text.create(c)
								.text(subtitle)
								.textSizeSp(20))
				.build();
	}
}