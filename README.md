# AlertView-Android 
Android Library to show a beautiful Alert View
<br/>
[![](https://jitpack.io/v/rasoulmiri/skeleton.svg)](https://jitpack.io/#rasoulmiri/skeleton)
[![Android Arsenal]( https://img.shields.io/badge/Android%20Arsenal-AlertView--Android-green.svg?style=flat )]( https://android-arsenal.com/details/1/6404 )
<br/>
Minimum API 14
<br/><br/>
![alt tag](https://github.com/amspayam/AlertView-Android/blob/master/demo/demo.gif)
<br/><br/>
[Sample project](https://github.com/amspayam/AlertView-Android/tree/master/app)
## Usage:
#### Step 1

Add JitPack repository in your root build.gradle at the end of repositories.

    allprojects {
        repositories {
    	    ...
    	    maven { url 'https://jitpack.io' }
        }
    }
   
Add dependency in your app level build.gradle.

    dependencies {
	        compile 'com.github.amspayam:AlertView-Android:1.1.0'
	}
   
#### Step 2
use in Java Code
```Java
new AlertView().show(context, stringMessage, AlertView.STATE_ERROR);
```

```Java
new AlertView(context, stringMessage, AlertView.STATE_RELOAD) {
    @Override
    public void onRefresh() {
        super.onRefresh();
        //Methods you need to call on reload click
    }
};
```
