# WebClient Integration – External Books

- Implemented Open Library integration using Spring WebClient to fetch external books
- Configured properties: `openlibrary.base-url`, `search-path`, `default-limit`, `max-limit`
- Created dedicated WebClient bean (WebClientConfig.java)
- Built OpenLibraryClient with `/search.json`, query + limit, 5s timeout, fallback to empty response
- Added DTO layer: OpenLibrarySearchResponseDTO, OpenLibraryDocDTO, ExternalBookResponseDTO
- Developed service logic with query validation, limit caps, and response mapping
- Exposed endpoint: `GET /api/external-books/search?query=&limit=`
- Enabled public access in SecurityConfig
- Added controller test; all tests passed successfully  