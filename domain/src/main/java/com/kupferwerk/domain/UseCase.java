package com.kupferwerk.domain;

import com.kupferwerk.domain.executor.PostExecutionThread;
import com.kupferwerk.domain.executor.ThreadExecutor;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class UseCase<T, Params> {

   private final PostExecutionThread postExecutionThread;
   private final ThreadExecutor threadExecutor;
   private CompositeDisposable disposables;

   public UseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
      this.threadExecutor = threadExecutor;
      this.postExecutionThread = postExecutionThread;
   }

   public abstract Observable<T> buildUseCaseObservable(Params params);

   public void dispose() {
      if (disposables != null && !disposables.isDisposed()) {
         disposables.dispose();
      }
   }

   public void execute(DisposableObserver<T> observer) {
      execute(observer, null);
   }

   public Observable<T> execute(Params params) {
      return buildUseCaseObservable(params);
   }

   public void execute(DisposableObserver<T> observer, Params params) {
      dispose();
      final Observable<T> observable = this.buildUseCaseObservable(params)
            .subscribeOn(Schedulers.from(threadExecutor))
            .observeOn(postExecutionThread.getScheduler());
      addDisposable(observable.subscribeWith(observer));
   }

   private void addDisposable(Disposable disposable) {
      disposables = new CompositeDisposable();
      disposables.add(disposable);
   }
}
