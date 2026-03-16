# Book Search Functionality

A search functionality was implemented to help users easily find books using a specific search keyword or pattern. In the service layer, the method `searchBooks(String searchPattern)` receives the search input from the user and forwards it to the repository method `searchBooksByName(searchPattern)`, which retrieves matching book records from the database.

The retrieved results are then returned to the controller, which sends the matching data back to the client. This feature enhances the usability of the library system by allowing users to quickly and efficiently search for books.