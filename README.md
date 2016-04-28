# RxEvent
A simple EventBus for communication/sending message between Activity,Fragment,thread,or any component. Base on [Rxjava](https://github.com/ReactiveX/RxJava)
and [Kaushik Gopal's blog](http://nerds.weddingpartyapp.com/tech/2014/12/24/implementing-an-event-bus-with-rxjava-rxbus/)

## Notice 

- RxEvent is the simplest way to communicate in app, also with least codes to write. But Please do not heavy use it , that would be  harmful to your app infrastructure.
- RxEvent would sent `Object` instantly, so make sure Activity/Fragment in it's lifecycle ,when you want emit messages to them.
- if your Activity/Fragment extend from  `BaseActivity`/`BaseFragment`,RxEvent would unRegister automatically, when the lifecycle is `onStop()`.
 
Usage
=====

STEP 1 
------

Add dependency to your gradle file:

`compile 'com.muximus3:RxEvent:1.0.0'`

STEP 2
------

The simplest way of using RxEvent is extending your Activity/Fragment from `BaseActivity`/`BaseFragment`,

you must override two methods
`protected abstract boolean wantMission();
   protected abstract void onNewMission(Object object);
`
- ###get a message/mission from anywhere

You should return `true` in `wantMission()`, if you want obtain mission/message, otherwise `false`.
Then in `onNewMission()`,just write detail operation about the message.

``` java
public class MainActivity extends BaseActivity {
        ... 
       @Override
       protected void onCreate(Bundle savedInstanceState) {
        ...
       }
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

### Send a message/mission to anywhere

``` java
if (RxBus.getInstance().hasObservers()) RxBus.getInstance().sendMission(objectInstance);
```


### Other use (eg: in your own view)

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
- ### Detail usage check the 

[sample](https://github.com/muximus3/RxEvent/tree/master/sample/src/main/java/sample)



 



