ContributionsView
=================

[![](https://jitpack.io/v/hiroaki-dev/ContributionsView.svg)](https://jitpack.io/#hiroaki-dev/ContributionsView)


<img src=https://raw.githubusercontent.com/hiroaki-dev/ContributionsView/images/screenshot1.png width="300" />


Usage
=====

* Project level `build.gradle`

	```groovy
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	```

* App level `build.gradle`

	```groovy
	dependencies {
			implementation 'com.github.hiroaki-dev:ContributionsView:v0.1'
	}
	```

* Your xml layouts

	```xml
	<me.hiroaki.contributionsview.ContributionsView
        android:id="@+id/contributionsView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:padding="16dp"
        app:day_of_week_font_size="12sp"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:month_font_size="12sp"
        app:square_size="8dp"/>
	```

Credits
=======
I used [ThreeTenABP](https://github.com/JakeWharton/ThreeTenABP) library by [Jake Wharton](https://github.com/JakeWharton).

License
=======

   Copyright 2018 Yuki Nakakura

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
