# ViewPagerwithIndicator

[![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=15)
[![MIT licensed](https://img.shields.io/badge/license-MIT-blue.svg)](https://raw.githubusercontent.com/hyperium/hyper/master/LICENSE)
[![Release](https://jitpack.io/v/kassisdion/ViewPagerWithIndicator.svg)](https://jitpack.io/#kassisdion/ViewPagerWithIndicator
)

## What's ViewPagerwithIndicator ?
A simple ViewPager indicator implementation compatible with the Android Support Library.
It can use arrows on the left and on right and it can display a pageIndicator.

## Demo
![demo](https://raw.githubusercontent.com/kassisdion/ViewPagerWithIndicator/1352b989151d8427e41850c432ed74e4cee5a1a7/doc/demo_gif.gif)

## Feature
* Can enable/desable Arrows
* Can select arrows width/height
* Can custom arrows drawable
* Can enable/desable PageIndicator
* Can select PageIndicator size
* Can custom PageIndicator colors (default/selected)

## Import
[JitPack](https://jitpack.io/)

Add it in your project's build.gradle at the end of repositories:

```gradle
repositories {
  // ...
  maven { url "https://jitpack.io" }
}
```

Step 2. Add the dependency in the form

```gradle
dependencies {
  compile 'com.github.kassisdion:ViewPagerWithIndicator:v1.0'
}
```

## Usage
#### Step 1 : Define your viewPager under your xml (This should usually be placed adjacent to the viewPager) :

```java
    <com.kassisdion.library.ViewPagerWithIndicator
        app:arrow.enable="true"
        app:arrow.width="50dip"
        app:arrow.height="50dip"
        app:left_arrow.drawable="@drawable/ic_action_arrow_left"
        app:right_arrow.drawable="@drawable/ic_action_arrow_right"
        app:round.enable="true"
        app:round.size="15dip"
        app:round.drawable="@drawable/background_rounded"
        app:round.color.default="@android:color/white"
        app:round.color.selected="@android:color/holo_blue_light"
        android:id="@+id/viewPagerWithIndicator"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

    </com.kassisdion.library.ViewPagerWithIndicator>
```

#### Step 2 : Initialize the ViewPagerIndicator under your activity (or fragment) :

```java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Set the pager with an adapter
        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        List<Integer> objects = Arrays.asList(Color.BLUE, Color.GREEN, Color.RED);
        pager.setAdapter(new ViewPagerCustomAdapter(objects));
        
        //Bind the title indicator to the adapter
        ViewPagerWithIndicator viewPagerWithIndicator = (ViewPagerWithIndicator)findViewById(R.id.viewPagerWithIndicator);
        viewPagerWithIndicator.setViewPager(pager);
    }
```

## Sample
For a working implementation of this project see the app/ folder.

## Javadoc
You can take a look at the [javadoc](https://jitpack.io/com/github/kassisdion/ViewPagerWithIndicator/v1.0/javadoc/)

## License
See  LICENSE.txt

## Contact
If you have any new idea about this project, feel free to [contact me](mailto:florian.faisant@gmail.com).

