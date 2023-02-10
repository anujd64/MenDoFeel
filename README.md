# MenDoFeel Assignment

- MVVM architecture
- Retrofit to make api calls
- Recyclerview-selection library for selection of multiple items
- Longpress to select an item
- The search uses Filterable to filter items in the recyclerview  
- The selection is retained after searching (If you select items then search and return to original state the selection remains intact but you can not add new items to your selection during a search)

But there are a few shortcomings 
- We can not select an item while searching
- Selection is not retained on orientation change
