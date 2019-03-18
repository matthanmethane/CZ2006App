## Folder Structure
1. ./db
- Used to store the database logic.
1.1. ./db/dao
	- Used to store the Data Access Object associated with each entity. A Data Access Object is an abstraction to each entity.
1.2. ./db/entity
	- Used to store each entity (e.g. School, Courses, etc)
1.3. ./db/views
	- Used to store views (instead of entities to save storage space)
	
2. ./ui
- Used to generate the user interface. MainActivity is the main entry point.
	- SchoolListFragment is used to generate the schoolEntity summary textboxes
together with SchoolsAdapter.
	- SchoolFullTextboxFragent is to be used to generate the full textbox with the details for a certain schoolEntity.
	- BindingAdapters is a class used to store all methods pertaining to binding adapters.
	- SchoolsAdapter is used for the recycler view that is found in the School List fragment. Generates a summary textbox for each 
	schoolEntity in the search result.
	
3. ./viewmodel
- all the stores of data to be used throughout the lifecycle of an activity. This ensures data does not get destroyed in the event of a
change in screen orientation and other ways to change the state of an activity. https://developer.android.com/topic/libraries/architecture/viewmodel.html

4. AppExecutors
- contains background tasks to be run when the app starts.

5. DataRepository
- provides a layer of abstraction between the application logic and the database logic.

6. EmpathyApp
- links together stuff