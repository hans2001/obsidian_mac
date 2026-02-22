# What was your approach for real-time filtering in flutter?

1. **Controller-Listener Pattern**: I used a `TextEditingController` connected to the search TextField and attached a listener that triggers the filtering logic whenever the text changes.
	listener attached to the field (event triggering approach) 

2. **Efficient Filtering Logic**: I implemented the filtering using Dart's collection methods (specifically `where()` and `map()`) to filter the merchant data based on case-insensitive string matching. 

3. **Reactive State Management**: The filtering logic updates a dedicated `searchSuggestions` list in the widget's state, which causes Flutter to rebuild only the relevant UI portions when the state changes.
	- [ ] flutter maintain a lightweight widget tree just like the virtual DOM that react maintains! 
	- [ ] when changes occur, new widget tree is built and diff is compared!
	- [ ] we update the part where tree differs! 
	- [ ] only widget that depend on this data (search suggestions) will be rebuild! 

Widget is like component in react, where only depended component will render when some state changes!
## Widget state 
**State Object:** 
	separate class that extends `State<T>` where T is your StatefulWidget
contains mutable data
```dart
class SearchScreenState extends State<SearchScreen> {
  List<String> searchSuggestions = [];
  // Other state variables...
  
  @override
  Widget build(BuildContext context) {
    // UI that depends on searchSuggestions
  }
}
```

**State lifecycle**
	persist across rebuilds of related widget
	can be modified through setState ()

**setState**
	when setState is called, the widget will be marked as dirty! 
	flutter schedules a rebuilt of this widget and descendants

**Stateless widget**
	cannot hold mutable state
	once built, cannot change! 
	ideal for UIs that don' have  internal states 

**Stateful widget**
	contains mutable state
	updated through setState( )
	state persists between rebuilds? 
		old widget instance is discarded
		but the old state persist and inherit to the new widget instance that is built!
# Code samples 
## Controller-Listener
```dart
// Define the controller
TextEditingController _searchController = TextEditingController();

// Set up the listener in initState
@override
void initState() {
  super.initState();
  loadData();
  _searchController.addListener(_onSearchChanged);
}

// Connect controller to TextField in UI
Widget _buildSearchBar() {
  return Container(
    // ...
    child: TextField(
      controller: _searchController,
      style: TextStyle(color: Colors.white),
      decoration: InputDecoration(
        hintText: 'Search',
        // ...
      ),
    ),
    // ...
  );
}

// Clean up resources
@override
void dispose() {
  _searchController.dispose();
  super.dispose();
}
```
controller tracks user input change
##  Reactive State Management
```dart
// Define state variable for search results
List<String> searchSuggestions = [];

// Update state with new suggestions
void _onSearchChanged() {
  String query = _searchController.text.toLowerCase();
  if (query.isEmpty) {
    setState(() => searchSuggestions = []);
    return;
  }

  List<String> suggestions = merchants
      .where((merchant) => merchant['brandName'].toLowerCase().contains(query))
      .map((merchant) => merchant['brandName'] as String)
      .toList();

  setState(() => searchSuggestions = suggestions);
}

// Conditionally render different UI based on search state
@override
Widget build(BuildContext context) {
  return Scaffold(
    // ...
    body: Stack(
      children: [
        // ...
        Column(
          children: [
            _buildSearchBar(),
            Expanded(
              child: ListView(
                children: [
                  if (_searchController.text.isEmpty) ...[
                    _buildSearchSection('Recent Searches', recentSearches),
                    _buildSearchSection('Popular Searches', popularSearches),
                  ] else
                    _buildSuggestionsList(),
                ],
              ),
            ),
          ],
        ),
      ],
    ),
  );
}
```
update UI when search results change
## Efficient Filtering Logic
```dart
void _onSearchChanged() {
  String query = _searchController.text.toLowerCase();
  if (query.isEmpty) {
    setState(() => searchSuggestions = []);
    return;
  }

  // Efficient collection methods for filtering
  List<String> suggestions = merchants
      .where((merchant) => merchant['brandName'].toLowerCase().contains(query))
      .map((merchant) => merchant['brandName'] as String)
      .toList();

  setState(() => searchSuggestions = suggestions);
}

// Display the filtered results
Widget _buildSuggestionsList() {
  return ListView.builder(
    shrinkWrap: true,
    physics: NeverScrollableScrollPhysics(),
    itemCount: searchSuggestions.length,
    itemBuilder: (context, index) {
      return ListTile(
        leading: Icon(Icons.search, color: Colors.white),
        title: Text(searchSuggestions[index], style: TextStyle(color: Colors.white)),
        onTap: () => _navigateToMerchantPage(searchSuggestions[index]),
      );
    },
  );
}
```
process the data collection