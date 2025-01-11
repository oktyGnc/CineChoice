package com.oktaygenc.cinechoice

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp // This annotation triggers Hilt's code generation and allows dependency injection to be available throughout the app.
class MovieApplication : Application()