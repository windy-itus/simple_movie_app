# Simple Movie App

## Description
It is a simple list and detail app using TMDB(https://developer.themoviedb.org/) API to show today's trending movies using the recommended Android Jetpack libraries.

## Requirements
- **Minimum API Level**: The SDK requires Android API level 24 (Android 6.0, Marshmallow) or higher.
- **Network Connection**: The app needs a network connection to fully use features.
- **Gradle JDK**: JDK 17 or higher.

## Setup
After cloning project, Please create new file `properties/config.properties` at root project.
Add config fields `SERVER_ENDPOINT` and `SERVER_API_KEY`

Example:
```config.properties
SERVER_ENDPOINT=https://api.themoviedb.org/
SERVER_API_KEY={{YOUR_API_KEY}}
```

## Patterns and Third party libraries applied
1. Android Jetpack libraries (Livedata, ViewModel, Pagination, Room, Navigation, etc)
2. MVVM
3. Retrofit2
4. Coroutines
5. Material Design

## Features
1. Trending movies: Online/offline
2. Search movies: Online
3. Movie details: Online/offline

## Unit Tests
1. Caching
2. Trending Movies and Search Movies

## Support
For support, please contact <khoapham.wrk@gmail.com>.

## Additional Note
Feel free to make some pull to improvement it.

## License
[MIT License](https://github.com/windy-itus/simple_movie_app?tab=MIT-1-ov-file)