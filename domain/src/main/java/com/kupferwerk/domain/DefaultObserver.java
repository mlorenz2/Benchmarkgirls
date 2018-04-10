package com.kupferwerk.domain;

import io.reactivex.observers.DisposableObserver;

public class DefaultObserver<T> extends DisposableObserver<T> {

   @Override
   public void onComplete() {

   }

   @Override
   public void onError(Throwable error) {

   }

   @Override
   public void onNext(T value) {

   }
}
