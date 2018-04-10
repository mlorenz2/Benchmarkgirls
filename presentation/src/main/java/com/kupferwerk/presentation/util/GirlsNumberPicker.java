package com.kupferwerk.presentation.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.NumberPicker;

public class GirlsNumberPicker extends NumberPicker {
   public GirlsNumberPicker(Context context) {
      super(context);
   }

   public GirlsNumberPicker(Context context, AttributeSet attrs) {
      super(context, attrs);
      processAttributes(attrs);
   }

   public GirlsNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
      super(context, attrs, defStyleAttr);
      processAttributes(attrs);
   }

   private void processAttributes(AttributeSet attrs) {
      this.setMinValue(attrs.getAttributeIntValue(null, "min", 0));
      this.setMaxValue(attrs.getAttributeIntValue(null, "max", 100));
      this.setValue(attrs.getAttributeIntValue(null, "initial", 0));
   }
}
