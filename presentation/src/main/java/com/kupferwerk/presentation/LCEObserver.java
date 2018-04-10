package com.kupferwerk.presentation;

import android.util.Log;

import com.kupferwerk.domain.DefaultObserver;
import com.kupferwerk.presentation.core.BaseView;

public class LCEObserver<T> extends DefaultObserver<T> {

   private BaseView baseView;

   public LCEObserver(BaseView baseView) {
      this.baseView = baseView;
   }

   @Override
   public void onComplete() {
      super.onComplete();
      baseView.showError(false);
      baseView.showContent(true);
      baseView.showLoading(false);
   }

   @Override
   public void onError(Throwable error) {
      super.onError(error);
      baseView.showError(true);
      baseView.showContent(false);
      baseView.showLoading(false);
   }

   @Override
   public void onNext(T value) {
      super.onNext(value);
      baseView.showError(false);
      baseView.showContent(true);
      baseView.showLoading(false);
   }
}
