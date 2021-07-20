# Release Notes

### Libraries/Components I used for Sixt Coding Task:

  * Android Jetpack Architecture
  * Retrofit REST API Calls
  * Google Maps & Play Services
  * Glide as Image Loader Library
  * Dagger Hilt for Dependency Injection
  * Activity KTX & Fragment KTX
  * Espresso Testing Dependencies
  
### Why did I choose these libraries?
  
  * Jetpack ViewModel is implemented to save and manage UI related data in the App Lifecycle, this class allows to keep data to survive different configurations
                      such as screen rotations. <br><br>@IMPORTANT VIEWMODEL NOTE: as the app required only needs one function to be called from ViewModel, I decided to implement only one
                      ViewModel class and not two as it should be due to the Clean Code Values.<br>
                      
  * Jetpack Navigation Component is implemented for its dynamism to navigate through fragments, handles an organized backStack, animations, and fragment transactions
                      pretty well clean.
                      
  * Jetpack LiveData, implemented to observe real time live-data from API calls in ViewModel, in this context it will update our UI with new data state as there are changes in the DB.
  * Jetpack ViewBinding allows to access all views in a simple way reducing a lot of boilerplate and time.
  
  * Retrofit REST API Calls is used in this context to fetch JSON in the endpoint provided, fetching the response body will be serialized in an object class.
  
  * Coroutines in this case is used to execute a REST API call in secondary threads, preventing blocking the main-thread (UI), which would produce ANRs errors.
  
  * Google Maps & Play Services within this context it will render the map and mark the cars locations in the map.
  
  * Glide as Image Loader will execute the task of loading the URL images into the desired view, plus will help us to check if there is any loadError such as 404 error, in that case
  a fallback image will be loaded.
  
  * Activity KTX & Fragment KTX will allow us to write more concise and idiomatic code such as "by viewModel"... etc.
  
  * Dagger Hilt for Dependency Injection, candy of the app, defining the modules, we will be able to create our singletons and provide them anywhere we want, makins classes independent of its dependencies,
  which will let us to create much more modular code, being able to replace dependencies without changing the class itself, in this case
  is responsible to provide the singleton of the REST API Call (Retrofit) and the CarRepository as it is an interface will return us the class where is implemented. I selected Dagger Hilt 
  for its simplicity and dynamism to inject dependencies.
  
  * Espresso Testing Dependencies predictable and easy to use testing dependency, reduced distraction because its low boilerplate content. In this case I have tested some of the recyclerView 
  behavior.

