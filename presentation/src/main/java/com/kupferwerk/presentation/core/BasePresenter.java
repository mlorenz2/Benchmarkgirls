package com.kupferwerk.presentation.core;


public interface BasePresenter<V extends BaseView> {

   void onBind();

   void onUnbind();

   void setView(V baseView);

}
