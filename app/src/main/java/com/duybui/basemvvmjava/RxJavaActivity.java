package com.duybui.basemvvmjava;

import android.os.Bundle;

import com.duybui.basemvvmjava.data.models.User;
import com.duybui.basemvvmjava.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import io.reactivex.*;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class RxJavaActivity extends BaseActivity {
    private static final String TAG = RxJavaActivity.class.getSimpleName();

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
        flatmapOperator();
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
