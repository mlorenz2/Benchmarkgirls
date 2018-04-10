package com.kupferwerk.presentation.util;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class TitleCustomFont extends AppCompatTextView {
   public TitleCustomFont(Context context) {
      super(context);
      createTypeface(context);
   }

   public TitleCustomFont(Context context, AttributeSet attrs) {
      super(context, attrs);
      createTypeface(context);
   }

   public TitleCustomFont(Context context, AttributeSet attrs, int defStyleAttr) {
      super(context, attrs, defStyleAttr);
      createTypeface(context);
   }

   private void createTypeface(Context context) {
      setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Caveat-Regular.ttf"));
   }
}
