package com.duybui.basemvvmjava;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Pair;
import androidx.annotation.Nullable;
import com.duybui.basemvvmjava.data.models.User;
import com.duybui.basemvvmjava.ui.base.BaseActivity;
import io.reactivex.*;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class RxJavaActivity extends BaseActivity {
    private static final String TAG = RxJavaActivity.class.getSimpleName();

    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    public int getLayoutRes() {
        return R.layout.activity_rx;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // doSomeWork();
        //doSomeWorkFlowable();
        // doSomeWorkSingle();
        //doSomethingMaybe();
        //mapOperator();
        //zipOperator();
        //flatmapOperator();
        //flatMapWithZipOperator();
        //publishSubject();
        //replaySubject();
        //behaviorSubject();
        //asyncSubject();
        // disposableExamples();
        backPressureExample();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

    private void doSomeWork() {
        /*
        Observable: It emits 0..N elements, and then completes successfully or with an error.
         */
        getObservable().subscribeOn(Schedulers.io())
                       .observeOn(AndroidSchedulers.mainThread())
                       .subscribe(getObserver());
    }

    private void doSomeWorkFlowable() {
        /*
        Flowable: Similar to Observable but with a backpressure strategy.
         */
        getFlowable().subscribeOn(Schedulers.io())
                     .observeOn(AndroidSchedulers.mainThread())
                     .subscribe(s -> System.out.println(s));
    }

    private void doSomeWorkSingle() {
        //Single: It completes with a value successfully or an error.(doesn’t have onComplete callback,
        // instead onSuccess(val)).
        Single.just("Cricket").subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(new SingleObserver<String>() {
                  @Override
                  public void onSubscribe(Disposable d) {

                  }

                  @Override
                  public void onSuccess(String value) {
                      System.out.println(value);
                  }

                  @Override
                  public void onError(Throwable e) {

                  }
              });

    }

    private void doSomethingMaybe() {
        /*
        Maybe: It completes with/without a value or completes with an error.
         */
        Maybe.just("Volleyball").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
             .subscribe(new MaybeObserver<String>() {
                 @Override
                 public void onSubscribe(Disposable d) {

                 }

                 @Override
                 public void onSuccess(String value) {
                     System.out.println(value);
                 }

                 @Override
                 public void onError(Throwable e) {

                 }

                 @Override
                 public void onComplete() {

                 }
             });
    }

    private void mapOperator() {
        /*
        Map transforms the items emitted by an Observable by applying a function to each item.
         */
        getObservableUser().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                           .map(users -> users).subscribe(new Observer<List<User>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<User> value) {
                for (User user : value) {
                    System.out.println(user.getEmail());
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    private void zipOperator() {
        /*
        Zip combines the emissions of multiple Observables together via a specified function,
        then emits a single item for each combination based on the results of this function
         */
        Observable.zip(observableListUserFromAppliedMesh(),
                       observableListUserFromEdgeworks(),
                       (usersAppliedMesh, usersEdgeworks) -> {
                           List<User> userListZip = new ArrayList<>();
                           for (User user : usersAppliedMesh) {
                               if (user.getEmail().contains("@appliedmesh.com")) {
                                   userListZip.add(user);
                               }
                           }

                           for (User user : usersEdgeworks) {
                               if (user.getEmail().contains("@edgeworks.net")) {
                                   userListZip.add(user);
                               }
                           }
                           return userListZip;
                       })
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new Observer<List<User>>() {
                      @Override
                      public void onSubscribe(Disposable d) {

                      }

                      @Override
                      public void onNext(List<User> value) {
                          for (User user : value) {
                              System.out.println(user.getEmail());
                          }
                      }

                      @Override
                      public void onError(Throwable e) {

                      }

                      @Override
                      public void onComplete() {

                      }
                  });
    }

    private void flatmapOperator() {
        /*
        FlatMap transforms the items emitted by an Observable into Observables,
        then flattens the emissions from those into a single Observable.
         */
        observableListUserFromAppliedMesh().flatMap((Function<List<User>, ObservableSource<User>>) users -> {
            // flatMap - to return users one by one
            return Observable.fromIterable(users);
        })
                                           .filter(user -> {
                                               if (user.getGender().equalsIgnoreCase("Female")) {
                                                   return true;
                                               }
                                               return false;
                                           })
                                           .subscribeOn(Schedulers.io())
                                           .observeOn(AndroidSchedulers.mainThread())
                                           .toList()
                                           .subscribe(new SingleObserver<List<User>>() {
                                               @Override
                                               public void onSubscribe(Disposable d) {

                                               }

                                               @Override
                                               public void onSuccess(List<User> value) {
                                                   for (User user : value) {
                                                       System.out.println(user.getEmail());
                                                   }
                                               }

                                               @Override
                                               public void onError(Throwable e) {

                                               }
                                           });
    }

    private void flatMapWithZipOperator() {
        observableListUserFromAppliedMesh().flatMap((Function<List<User>, ObservableSource<User>>) users -> {
            // returning user one by one from usersList.
            return Observable.fromIterable(users);
        })
                                           .flatMap((Function<User, ObservableSource<Pair<User, User>>>) user -> Observable
                                                   .zip(getUserDetail(user),
                                                        Observable.just(user),
                                                        (user1, user2) -> new Pair<>(
                                                                user1, user2)))
                                           .subscribeOn(Schedulers.io())
                                           .observeOn(AndroidSchedulers.mainThread())
                                           .subscribe(new Observer<Pair<User, User>>() {
                                               @Override
                                               public void onSubscribe(Disposable d) {

                                               }

                                               @Override
                                               public void onNext(Pair<User, User> value) {

                                               }

                                               @Override
                                               public void onError(Throwable e) {

                                               }

                                               @Override
                                               public void onComplete() {

                                               }
                                           });
    }

    private void backPressureExample() {
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                int count = 0;
                while (count < Long.MAX_VALUE) {
                    count++;
                    e.onNext(count + "\n");
                }
            }
        }, BackpressureStrategy.DROP).observeOn(Schedulers.newThread(), false, 3)
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {

                    }

                    @Override
                    public void onNext(String s) {
                        try {
                            Thread.sleep(1000);
                            System.out.println(s);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void disposableExamples() {
        disposable.add(sampleObservable().subscribeOn(Schedulers.io())
                                         .observeOn(AndroidSchedulers.mainThread())
                                         .subscribeWith(new DisposableObserver<String>() {
                                             @Override
                                             public void onComplete() {

                                             }

                                             @Override
                                             public void onError(Throwable e) {

                                             }

                                             @Override
                                             public void onNext(String value) {
                                                 System.out.println(value);
                                             }
                                         }));
    }

    private Observable<String> sampleObservable() {
        return Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                // Do some long running operation
                SystemClock.sleep(2000);
                return Observable.just("one", "two", "three", "four", "five");
            }
        });
    }

    private void asyncSubject() {
        /*
        It only emits the last value of the source Observable(and only the last value)
         */

        AsyncSubject<Integer> source = AsyncSubject.create();
        // It will get only 4 and onComplete
        source.subscribe(getFirstObserver());
        source.onNext(1);
        source.onNext(2);
        source.onNext(3);
        // It will also get only get 4 and onComplete
        source.subscribe(getSecondObserver());
        source.onNext(4);
        source.onComplete();
    }

    private void behaviorSubject() {
        /*
        It emits the most recently emitted item and all the subsequent items of the source Observable when an observer subscribes to it.
         */
        BehaviorSubject<Integer> source = BehaviorSubject.create();
        source.subscribe(getFirstObserver());
        source.onNext(1);
        source.onNext(2);
        source.onNext(3);
        source.subscribe(getSecondObserver());
        source.onNext(4);
        source.onComplete();

    }

    private void replaySubject() {
        /*
        It emits all the items of the source Observable, regardless of when the subscriber subscribes.
         */

        ReplaySubject<Integer> source = ReplaySubject.create();
        // It will get 1, 2, 3, 4
        source.subscribe(getFirstObserver());
        source.onNext(1);
        source.onNext(2);
        source.onNext(3);
        source.onNext(4);
        source.onComplete();

        source.subscribe(getSecondObserver());
    }

    private void publishSubject() {
        /*
        It emits all the subsequent items of the source Observable at the time of subscription
         */
        PublishSubject<Integer> source = PublishSubject.create();

        source.subscribe(getFirstObserver());
        source.onNext(1);
        source.onNext(2);
        source.onNext(3);

        source.subscribe(getSecondObserver());
        source.onNext(4);
        source.onComplete();
    }

    private Observer<Integer> getFirstObserver() {
        return new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println(" First onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onNext(Integer value) {
                System.out.println(" First onNext : value : " + value);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println(" First onError : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println(" First onComplete");
            }
        };
    }

    private Observer<Integer> getSecondObserver() {
        return new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println(" Second onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onNext(Integer value) {
                System.out.println(" Second onNext : value : " + value);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println(" Second onError : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println(" Second onComplete");
            }
        };
    }

    private Observable<User> getUserDetail(User user) {
        return Observable.just(user);
    }

    private Observable<List<User>> getObservableUser() {
        return Observable.fromCallable(() -> {
            User user = new User("duy.bui@appliedmesh.com", "0968579729", "Male", null);
            List<User> userList = new ArrayList<>();
            userList.add(user);
            return userList;
        });
    }

    private Observable<List<User>> observableListUserFromAppliedMesh() {
        return Observable.fromCallable(() -> {
            User user = new User("duy.bui@appliedmesh.com", "0968579729", "Male", null);
            User user1 = new User("tai.tran@appliedmesh.com", "0968579729", "Female", null);
            User user2 = new User("chris.tran@appliedmesh.com", "0968579729", "Female", null);
            List<User> userList = new ArrayList<>();
            userList.add(user);
            userList.add(user1);
            userList.add(user2);
            return userList;
        });
    }

    private Observable<List<User>> observableListUserFromEdgeworks() {
        return Observable.fromCallable(() -> {
            User user = new User("duy.bui@edgeworks.net", "0968579729", "Male", null);
            User user1 = new User("uy.tai@edgeworks.net", "0968579729", "Male", null);
            List<User> userList = new ArrayList<>();
            userList.add(user);
            userList.add(user1);
            return userList;
        });
    }

    private Flowable<String> getFlowable() {
        return Flowable.just("Cricket", "Footballer");
    }

    private Observable<String> getObservable() {
        return Observable.just("Cricket", "Football");
    }

    private Observer<String> getObserver() {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String value) {
                System.out.println(value);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    private SingleObserver<String> getSingleObserve() {
        return new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(String value) {
                System.out.println(value);
            }

            @Override
            public void onError(Throwable e) {

            }
        };
    }
}
