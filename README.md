# RxEvent
Communication between Activity and Fragment has been troublesome，Especially when the Activity contains multilayer views，RxEvent provides A simple way for communication/sending message between Activity, Fragment, thread, or any component. Base on [Rxjava](https://github.com/ReactiveX/RxJava)
and [Kaushik Gopal's blog](http://nerds.weddingpartyapp.com/tech/2014/12/24/implementing-an-event-bus-with-rxjava-rxbus/)

## Notice 

- RxEvent can simplify  communicatiton in app. But Please do not heavy use it, cause it would be harmful to your app infrastructure. eg: In my project i use this for communication between a single Fragment item in ViewPager and Its Activity.
- RxEvent would sent `Object` instantly, so make sure Activity/Fragment in it's lifecycle, when you want emit messages to them.
- if your Activity/Fragment extend from  `BaseActivity`/`BaseFragment`, RxEvent would unRegister automatically, when the lifecycle is `onStop()`.
 
Usage
=====

STEP 1 
------

Add dependency to your gradle file:

`compile 'com.muximus3:RxEvent:1.0.0'`

STEP 2
------

The simplest way of using RxEvent is extending your Activity/Fragment from `BaseActivity`/`BaseFragment`,
if you already have a `BaseActivity`/`BaseFragment`, you may write [these codes](https://github.com/muximus3/RxEvent/blob/master/rxbus/src/main/java/rxbus/BaseActivity.java) in your `BaseActivity`/`BaseFragment`, or check "Other use" in the [bottom](#user-content-bottom).

you must override two methods:

``` java
protected abstract boolean wantMission();
protected abstract void onNewMission(Object object);
```

#### obtain events 

If you want obtain events, you should return `true` in `wantMission()`,otherwise `false`.
Then just write detail operation about the message in `onNewMission()`.

``` java
public class MainActivity extends BaseActivity {
        ... 

       // true when get new mission onNewMission() will be called
       @Override
       protected boolean wantMission() {
           return true;
       }
       
       @Override
       protected void onNewMission(Object object) {  //get mission
           if (object instanceof String)
               tv.setText("" + object);
   
       }

   }
```

### post events

``` java
if (RxBus.getInstance().hasObservers()) RxBus.getInstance().sendMission(objectInstance);
```



### Other use (eg: in your own view) <h1 id="user-content-bottom"></h1>
``` java
public class MyView extends .. {
       private  CompositeSubscription subscriptions;

       private  void init() {
               subscriptions = new CompositeSubscription();
               //subscribe 
               subscriptions.add(RxBus.getInstance().toObserverable().subscribe(object -> {
               //deal with the message
               }));
           }
       }
       //better call this method from it's Activity/Fragment, when `onStop()` get call
       public void onDestroyed(){
                subscriptions.unsubscribe();
       }
   } 
```
### Detail usage check the 
[sample](https://github.com/muximus3/RxEvent/tree/master/sample/src/main/java/sample)



 



