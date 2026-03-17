# Search API & Deletion Update

- Added search APIs: `/api/authors/search?query=` and `/api/books/search?query=`
- Author search: matches `firstName`, `lastName`, `country` (case-insensitive)
- Book search: matches `name`, `category`, `isbnNumber` (case-insensitive)
- Blank query returns all records
- Implemented full flow: Controller → Service → Repository
- Enhanced author deletion (many-to-many handling):
    - Remove author from co-authored books
    - Delete books where author is the only author
    - Then delete the author