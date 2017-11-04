# AlertView-Android
Android Library to show a beautiful Alert View

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
