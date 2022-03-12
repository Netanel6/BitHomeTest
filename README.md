# BitHomeTest


# General architecture of the application.

## Architecture
The main architecture oh the application Iv’e developed is MVVM.
Model-View-ViewModel, with Data & Settings repository to handle the data and settings all over the app dynamically. 
Once the app is opened we are getting only one instance for these classes (along with ConnectionLiveData class to get the state of the internet)

## Abstract class
Every fragment in the app is being inherited from an abstract class with 4 methods with is being called inside the class in the override method of the fragment class onActivityCreated:
1.observeNetworkState - observes the networks state via the viewModel 
2.initViews - initiates all the views in the class 
3.initClicks - sets the click listener as the current context 
4.onFragmentReady - all the other methods inside the class

## Coroutines 
To handle the data loading and saving I used Kotlin coroutines
It is a google’s library so we can agree that it isn’t a 3rd library all the way (more explained below)

## NavigationController and BottomNavigationView
The navigation of the app is through Navigation controller (nav_graph) & BottomNavigationView
1.The navigation controller is to handle onItemClicked through the recycler with listener
2.The BottomNavigationView is to navigate and filter movies as required and to navigate to the saved movies

## Movies
1.Saved movies is being saved into room persistence local database
2.The featured movies is being requested from the api and as long you will try to scroll down the view model will handle the page number incrementation and get more movies by increment the page number by 1 (Lazy loading)

## Utils 
Iv’e added some singleton classes (object) to prevent duplicates of code such as 
1.LoggerUtils - manages all the logs, toasts and snackbars
2.AppUtils - manages all the data manipulation


# Reasoning behind main technical choices.

## MVVM 
is the most common app building architeture.
using the observer and and live data objects we can get the data and observe changes in live and update them in the view instantly. - part of jetpack library

## Room persistence local db 
is the most common data storing library that works synchronously with SQL queries using annotations 
It is convenient and minimizes boiler-plate-code - part of jetpack library

## Coroutines 
is a very simple yet robust library to handle multithreading jobs on the worker thread it is more efficient and prevents ANRs and ui locks.
The main reason is that a coroutine can start on a certain thread and finish the job on a different thread unlike java threading or executer where you assign number of thread to the app. Meaning you can assign many coroutines scopes as you want and don’t block the parallel work in the background

## Retrofit
I used retrofit because every app  I maintained or developed Iv’e seen retrofit was being used
It is a common and easy library for rest API’s  for java and kotlin software developing language. The library provides a powerful framework for authenticating and interacting with APIs and sending network requests with OkHttp with API keys or bearer tokesns- I used API key but I know how to handle Bearer tokens with Base64 convertion. 

## Singleton 
for some parts of the code it’s the easiest way to write one dynamic with one instance for the app’s lifecycle to get the job done 
One example from the app is to place an image inside its’ holder once in the recycler view’s image view and the other in the image view in movie details fragment
Singleton one of many design patterns helping us building the app (more examples are: observer, builder, adapter etc.)

## Adapter
I’m using an adapter to show the data from the server or the local database , which is a design pattern as explained in chapter C ,
The main reason I decided to use recycler view adapter is because it can wrap the data and place it in the designated position through the recycler view as required. The prefect match between the adapter and the recycler view is kind of like a magic. The adapter do it’s work to handle the data and place it inside the recycler view and the recycler view as its’ name knows to show the data and recycle the view once it gets out of scope and save memory and loading time along with the layout manager we can decide how to show the data inside the recycler with one of three kind of layout managers 
1.LinearLayoutManager shows items in a vertical or horizontal scrolling list.
2.GridLayoutManager shows items in a grid.
3.StaggeredGridLayoutManager shows items in a staggered grid.
Recycler view is the inheriter of list and grid view.

# Features you didn't implement. This can also include details about how you would implement things differently if you were to spend more time on the assignment or if it was for production use.

I know about one thing I didn’t have time or enough exposure time and I really want to learn is Hilt Dependency Injection.
I know that I could have been used it in the app to inject instances all over the app, instead of returning instances from the main activity and “Inject manually” I know I could use Hilt 
