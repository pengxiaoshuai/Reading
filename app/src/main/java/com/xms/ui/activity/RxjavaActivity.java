package com.xms.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.xms.R;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxjavaActivity extends AppCompatActivity {
    private Button mbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        //init();
        //Create();
        //Map();
        //Zip();
        //Concat();
        //FlatMap();
        concatMap();
    }
    private void init(){
        final Flowable<String> flowable = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                e.onNext("hello RxJava");
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER);
        //也可以直接调用Flowable.just创建一个发射字符串的“发射器”
        //Flowable<String> flowable = Flowable.just("hello RxJava 2");

        final Subscriber subscriber = new Subscriber<String>(){
            @Override
            public void onSubscribe(Subscription s){
                Log.e("00000","onSubscribe");
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(String s){
                Log.e("00000",s);
            }

            @Override
            public void onError(Throwable t){
                Log.e("00000","error");
            }

            @Override
            public void onComplete(){
                Log.e("00000","onComplete");
            }
        };


        mbtn= (Button) findViewById(R.id.activity_rxjava_btn);
        mbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                flowable.subscribe(subscriber);
            }
        });

//        Consumer consumer = new Consumer<String>(){
//            @Override
//            public void accept(String s) throws Exception {
//                Log.e("00000",s);
//            }
//        };
//        flowable.subscribe(consumer);

        Flowable.just("hello RxJava 3")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e("00000",s);
                    }
                });

        //map的作用就变换 Flowable 然后返回一个指定类型的 Flowable 对象
        Flowable.just("map")
                .map(new Function<String, String>(){
                    @Override
                    public String apply(String s) throws Exception {
                        return s+"签名";
                    }
                })
                .subscribe(new Consumer<String>(){
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e("00000",s);
                    }
                });

        //map 操作符更神奇的地方是，你可以返回任意类型的 Flowable，也就是说你可以使用 map 操作符发射一个新的数据类型的 Flowable 对象。
        Flowable.just("map1")//3343957
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) throws Exception {
                        return s.hashCode();
                    }
                })
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return integer.toString();
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e("00000",s);
                    }
                });

    }
    //Create
    private void Create(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.e("00000","11111");
                e.onNext(1);
                Log.e("00000","22222");
                e.onNext(2);
                Log.e("00000","33333");
                e.onNext(3);
                e.onComplete();//当走了onComplete后，表示完成，下面就不会再接收事件了(但是上面的发送事件还在继续)
                Log.e("00000","44444");
                e.onNext(4);
            }
        }).subscribe(new Observer<Integer>(){
            private  int i;
            private Disposable mdisposable;
            @Override
            public void onSubscribe(Disposable d){
                //最开始会走这里面一次，然后onNext
                Log.e("00000",d.isDisposed()+"");
                //有一个Disposable概念，这个东西可以直接调用切断，可以看到，当它的isDisposed()返回为false的时候，接收器能正常接收事件，但当其为true的时候，接收器停止了接收。所以可以通过此参数动态控制接收事件了。
                mdisposable = d;
                i++;
                if (i==2){
                    mdisposable.dispose();
                    Log.e("00000disposable",mdisposable.isDisposed()+"");
                }
            }

            @Override
            public void onNext(Integer value){
              //  i++;
                 Log.e("000001",value+"");
            }

            @Override
            public void onError(Throwable e){
                Log.e("00000error",e.toString());
            }

            @Override
            public void onComplete() {
                Log.e("00000onComplete","00000");
            }
        });
    }
    //Map
    private void Map(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
            //map基本作用就是将一个Observable通过某种函数关系，转换为另一种Observable，上面例子中就是把我们的Integer数据变成了String类型
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return "this + " + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
            Log.e("00000",s+"");
            }
        });
    }
    private void Zip(){
        //zip专用于合并事件，该合并不是连接（连接操作符后面会说），而是两两配对，也就意味着，最终配对出的Observable发射事件数目只和少的那个相同。
        Observable.zip(getStringObservable(), getInterObservable(), new BiFunction<String, Integer, String>() {
            @Override
            public String apply(String s, Integer integer) throws Exception {
                return s+integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e("000001",s);
            }
        });
    }
    //单一的把两个发射器连接成一个发射器
    private void Concat(){
        Observable.concat(Observable.just(1,2,3,4), Observable.just(5,6,7))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e("00000",integer+"");
                    }
                });
    }
    //FlatMap可以把一个发射器Observable 通过某种方法转换为多个Observables，然后再把这些分散的Observables装进
    // 一个单一的发射器Observable。但有个需要注意的是，flatMap并不能保证事件的顺序，如果需要保证，需要用到我们下面要讲的ConcatMap。
    private void FlatMap(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < 3; i++){
                    list.add("I am "+integer);
                }
                int delayTime = (int) (1+Math.random() * 10);
                return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);
            }
        }).subscribe(new Consumer<String>(){
            @Override
            public void accept(String s) throws Exception {
                Log.e("00000",s);
            }
        });
    }
    //concatMap 与 FlatMap 的唯一区别就是 concatMap 保证了顺序
    private void concatMap(){
        Observable.create(new ObservableOnSubscribe<Integer>(){
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < 3; i++){
                    list.add("I am value" + integer);
                }
                int delayTime = (int) (1+Math.random()*10);
                return Observable.fromIterable(list).delay(delayTime,TimeUnit.MILLISECONDS);
            }
        }).subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e("00000",s);
                    }
                });
    }








    private Observable<String> getStringObservable(){
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                if (!e.isDisposed()){
                    Log.e("00000","A");
                    e.onNext("A");
                    Log.e("00000","B");
                    e.onNext("B");
                    Log.e("00000","C");
                    e.onNext("C");
                    Log.e("00000","D");
                    e.onNext("D");
                    Log.e("00000","E");
                    e.onNext("E");
                    Log.e("00000","F");
                    e.onNext("F");
                    Log.e("00000","G");
                    e.onNext("G");

                }
            }
        });
    }
    private Observable<Integer> getInterObservable(){
        return  Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                if (!e.isDisposed()){
                    Log.e("00000","1");
                    e.onNext(1);
                    Log.e("00000","2");
                    e.onNext(2);
                    Log.e("00000","3");
                    e.onNext(3);
                    Log.e("00000","4");
                    e.onNext(4);
                    Log.e("00000","5");
                    e.onNext(5);

                }
            }
        });
    }




}




