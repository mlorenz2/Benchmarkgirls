package com.kupferwerk.domain.executor;

import io.reactivex.Scheduler;

public interface PostExecutionThread {
   Scheduler getScheduler();
}