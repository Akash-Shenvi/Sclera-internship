# Today’s Update

* Implemented paginated user search endpoint:
  `GET /api/users`

    * Supports `query`, `page`, `size`, `sortBy`, `sortDir`
    * Returns `id`, `name`, `email`

* Implemented paginated user posts endpoint:
  `GET /api/users/{userId}/posts`

    * Supports `page`, `size`, `sortBy`, `sortDir`
    * Own user can see all posts, others can only see published posts

* Added pagination to:

    * `GET /api/posts`
    * `GET /api/comments/post/{postId}`

* Learned about unit testing using JUnit, Mockito, and MockMvc.
