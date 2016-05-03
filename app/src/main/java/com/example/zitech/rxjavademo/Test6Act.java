package com.example.zitech.rxjavademo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Action2;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.GroupedObservable;
import rx.subjects.ReplaySubject;
import rx.subjects.Subject;

/**
 * Created by pepe on 2016/4/27.
 * E_mail: 494778200@qq.com
 * Company:小知科技 http://www.zizizizizi.com/
 */
public class Test6Act extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test6);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test6_btn1://count
                count();
                break;
            case R.id.test6_btn3://reduce1
                reduce1();
                break;
            case R.id.test6_btn4://reduce2
                reduce2();
                break;
            case R.id.test6_btn5://reduce3
                reduce3();
                break;
            case R.id.test6_btn6://scan1
                scan1();
                break;
            case R.id.test6_btn7://scan2
                scan2();
                break;
            case R.id.test6_btn8://collect
                collect();
                break;
            case R.id.test6_btn9://toList
                toList();
                break;
            case R.id.test6_btn10://toSortedList
                toSortedList();
                break;
            case R.id.test6_btn11://toMap
                toMap();
                break;
            case R.id.test6_btn12://toMultimap
                toMultimap();
                break;
            case R.id.test6_btn13://groupBy
                groupBy();
                break;

        }
    }

    private void count() {
        Observable<Integer> values = Observable.range(0, 3);
        values.subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                log("values:Complete!");
            }

            @Override
            public void onError(Throwable e) {
                log("values:" + e.getMessage().toString());
            }

            @Override
            public void onNext(Integer integer) {
                log("values:" + integer);
            }
        });
        values.count()
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        log("count:Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log("count:" + e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        log("count:" + integer);
                    }
                });
    }


    private void reduce1() {
        Observable<Integer> values = Observable.range(0, 5);
        values.reduce(new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer integer, Integer integer2) {
                return integer + integer2;
            }
        })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        log("Sum:Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log("Sum:" + e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        log("Sum:" + integer);
                    }
                });

        values.reduce(new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer integer, Integer integer2) {
                return (integer > integer2) ? integer2 : integer;
            }
        })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        log("Min:Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log("Min:" + e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        log("Min:" + integer);
                    }
                });
    }

    private void reduce2() {
        Observable<String> values = Observable.just("Rx", "is", "easy");
        values.reduce(0, new Func2<Integer, String, Integer>() {
            @Override
            public Integer call(Integer integer, String s) {
                return integer + 1;
            }
        })
                //实现Last
//        values.reduce("", new Func2<String, String, String>() {
//            @Override
//            public String call(String s, String s2) {
//                return s2;
//            }
//        })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        log("Count:Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log("Count:" + e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        log("Count:" + integer);
                    }
                });
    }

    private void reduce3() {
        Observable<Integer> values = Observable.range(10, 5);
        values
                .reduce(new ArrayList<Integer>(), new Func2<ArrayList<Integer>, Integer, ArrayList<Integer>>() {
                    @Override
                    public ArrayList<Integer> call(ArrayList<Integer> integers, Integer integer) {
                        integers.add(integer);
                        return integers;
                    }
                })
//                .reduce(new ArrayList<Integer>(), new Func2<ArrayList<Integer>, Integer, ArrayList<Integer>>() {
//                    @Override
//                    public ArrayList<Integer> call(ArrayList<Integer> integers, Integer integer) {
//                        ArrayList<Integer> newAcc = (ArrayList<Integer>) integers.clone();
//                        newAcc.add(integer);
//                        return integers;
//                    }
//                })
                .subscribe(new Observer<ArrayList<Integer>>() {
                               @Override
                               public void onCompleted() {
                                   log("Complete!");
                               }

                               @Override
                               public void onError(Throwable e) {
                                   log(e.getMessage().toString());
                               }

                               @Override
                               public void onNext(ArrayList<Integer> arrayList) {
                                   log(arrayList.toString());
                               }
                           }

                );
    }

    private void scan1() {
        Observable<Integer> values = Observable.range(0, 5);
        values.scan(new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer integer, Integer integer2) {
                return integer + integer2;
            }
        })
//                .takeLast()//实现reduce
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        log("Sum:Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log("Sum:" + e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        log("Sum:" + integer);
                    }
                });
    }

    private void scan2() {
        Subject<Integer, Integer> values = ReplaySubject.create();
        values
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        log("Values:Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log("Values:" + e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        log("Values:" + integer);
                    }
                });
        values
                .scan(new Func2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer, Integer integer2) {
                        return (integer < integer2) ? integer : integer2;
                    }
                })
                .distinctUntilChanged()
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        log("Min:Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log("Min:" + e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        log("Min:" + integer);
                    }
                });
        values.onNext(2);
        values.onNext(3);
        values.onNext(1);
        values.onNext(4);
        values.onCompleted();
    }

    private void collect() {
        Observable<Integer> values = Observable.range(10, 5);
        values
                .collect(new Func0<ArrayList<Integer>>() {
                    @Override
                    public ArrayList<Integer> call() {
                        return new ArrayList<Integer>();
                    }
                }, new Action2<ArrayList<Integer>, Integer>() {
                    @Override
                    public void call(ArrayList<Integer> arrayList, Integer integer) {
                        arrayList.add(integer);

                    }
                })
                .subscribe(new Action1<ArrayList<Integer>>() {
                    @Override
                    public void call(ArrayList<Integer> integers) {
                        log(integers.toString());
                    }
                });

    }

    private void toList() {
        Observable<Integer> values = Observable.range(10, 5);
        values
                .toList()
                .subscribe(new Action1<List<Integer>>() {
                    @Override
                    public void call(List<Integer> integers) {
                        log(integers.toString());
                    }
                });

    }

    private void toSortedList() {
        Observable<Integer> values = Observable.range(10, 5);
        values
                .toSortedList(new Func2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer, Integer integer2) {
                        return integer2 - integer;
                    }
                })
                .subscribe(new Action1<List<Integer>>() {
                    @Override
                    public void call(List<Integer> integers) {
                        log(integers.toString());
                    }
                });
    }

    private void toMap() {
        Observable<Person> values = Observable.just(
                new Person("Will", 25),
                new Person("Nick", 40),
                new Person("Saul", 35)
        );
        values
                .toMap(new Func1<Person, String>() {
                    @Override
                    public String call(Person person) {
                        return person.name;
                    }
                })
//                .toMap(new Func1<Person, Object>() {
//                    @Override
//                    public Object call(Person person) {
//                        return person.name;
//                    }
//                }, new Func1<Person, Object>() {
//                    @Override
//                    public Object call(Person person) {
//                        return person.age;
//                    }
//                })
//                .toMap(new Func1<Person, Object>() {
//                    @Override
//                    public Object call(Person person) {
//                        return person.name;
//                    }
//                }, new Func1<Person, Object>() {
//                    @Override
//                    public Object call(Person person) {
//                        return person.age;
//                    }
//                }, new Func0<Map<Object, Object>>() {
//                    @Override
//                    public Map<Object, Object> call() {
//                        return new HashMap();
//                    }
//                })
                .subscribe(new Action1<Map>() {
                    @Override
                    public void call(Map mMap) {
                        log(mMap.toString());
                    }
                });
    }
    private void toMultimap(){
        Observable<Person> values = Observable.just(
                new Person("Will", 35),
                new Person("Nick", 40),
                new Person("Saul", 35)
        );
        values
//                .toMultimap(
//                        new Func1<Person, Object>() {
//                            @Override
//                            public Object call(Person person) {
//                                return person.age;
//                            }
//                        }, new Func1<Person, Object>() {
//                            @Override
//                            public Object call(Person person) {
//                                return person.name;
//                            }
//                        })
                .toMultimap(
                        new Func1<Person, Object>() {
                            @Override
                            public Object call(Person person) {
                                return person.age;
                            }
                        }, new Func1<Person, Object>() {
                            @Override
                            public Object call(Person person) {
                                return person.name;
                            }
                        }, new Func0<Map<Object, Collection<Object>>>() {
                            @Override
                            public Map<Object, Collection<Object>> call() {
                                return new HashMap();
                            }
                        }, new Func1<Object, Collection<Object>>() {
                            @Override
                            public Collection<Object> call(Object o) {
                                return new ArrayList();
                            }
                        }) // 没有使用这个 key 参数
                .subscribe(new Action1<Map>() {
                    @Override
                    public void call(Map mMap) {
                        log(mMap.toString());
                    }
                });
    }
    private void groupBy(){
        Observable<String> values = Observable.just(
                "first",
                "second",
                "third",
                "forth",
                "fifth",
                "sixth"
        );
        values.groupBy(new Func1<String, Object>() {
            @Override
            public Object call(String s) {
                return s.charAt(0);
            }
        })
//                .subscribe(new Action1<GroupedObservable<Object, String>>() {
//                    @Override
//                    public void call(final GroupedObservable<Object, String> objectStringGroupedObservable) {
//                        objectStringGroupedObservable.last().subscribe(new Action1<String>() {
//                            @Override
//                            public void call(String s) {
//                                log( objectStringGroupedObservable.getKey() +":" + s);
//                            }
//                        });
//                    }
//                });
                .subscribe(new Action1<GroupedObservable<Object, String>>() {
                    @Override
                    public void call(final GroupedObservable<Object, String> result) {
                        result.subscribe(new Action1<String>() {
                            @Override
                            public void call(String value) {
                                log("key:" + result.getKey() +", value:" + value);
                            }
                        });
                    }
                });
//                .flatMap(new Func1<GroupedObservable<Object, String>, Observable<?>>() {
//                    @Override
//                    public Observable<?> call(final GroupedObservable<Object, String> objectStringGroupedObservable) {
//                        return objectStringGroupedObservable.last().map(new Func1<String, Object>() {
//                            @Override
//                            public Object call(String s) {
//                                return objectStringGroupedObservable.getKey() +":" + s;
//                            }
//                        });
//                    }
//                })
//                .subscribe(new Action1<Object>() {
//                    @Override
//                    public void call(Object o) {
//                        log(o.toString());
//                    }
//                });




    }

    private class Person {
        public final String name;
        public final Integer age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    private void log(String string) {
        Log.d("pepe", string);
    }
}