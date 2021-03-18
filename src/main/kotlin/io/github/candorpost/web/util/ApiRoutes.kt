package io.github.candorpost.web.util

import java.util.*

val apiRoutesList: MutableSet<String> = HashSet()

val API_ROUTES = create("/api/routes")
val API_CATEGORIES_STORIES = create("/api/categories/stories")
val API_STORIES_NAME = create("/api/stories/:name")
val API_STORIES = create("/api/stories")
val API_CATEGORIES_POSTS = create("/api/categories/posts")
val API_POSTS_NAME = create("/api/posts/:name")
val API_POSTS = create("/api/posts")

private fun create(string: String): String {
	apiRoutesList.add(string)
	return string
}
