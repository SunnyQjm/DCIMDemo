[![](https://jitpack.io/v/SunnyQjm/DCIMDemo.svg)](https://jitpack.io/#SunnyQjm/DCIMDemo)
# DCIMDemo
这是一个简单的图片选择器

## 效果图
- ### 单选
  ![单选效果图](https://github.com/SunnyQjm/DCIMDemo/blob/master/single_select.jpg?raw=true)
- ### 多选
  ![多选效果图](https://github.com/SunnyQjm/DCIMDemo/blob/master/multi_select.jpg?raw=true)
- ### 切换相册集
  ![切换相册集效果图](https://github.com/SunnyQjm/DCIMDemo/blob/master/select_item.jpg?raw=true)
  
## 使用方式
- ### 首先，添加依赖
Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
```groovy
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

Step 2. Add the dependency
```groovy
dependencies {
  implementation 'com.github.SunnyQjm:DCIMDemo:v1.1.0'
}

```

- ### 启动相册
```kotlin
 EasyDCIM.with(this) // 需要一个Activity对象                 
          .setMode(EasyDCIM.MODE_SELECT_SINGLE)   //设置模式：多选/单选
          .setSaveState(false)                    //设置是否保存上一次的选择状态
          .setEasyBarParam(EasyBarParams(titleRes = R.string.title, barBgColor = R.color.colorAccent,
                  rightRes = R.drawable.cat))     //设置相册选择器中Bar的样式
          .jumpForResult(0)                       //传入一个requestCode
```
- ### 回调中接收结果
```kotlin
// 不管是单选还是多选，相册选择器返回的都是一个字符串数组，每个元素代表一张被选中图片的path
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when(requestCode){
      0 -> {
            data?.getStringArrayExtra(DCIMActivity.RESULT_PATHS)?.let {
                result.text = Arrays.toString(it)
            }
        }
    }
}
```

- ### EasyBarParams 参数说明
```Kotlin
class EasyBarParams(val title: String = "",                                     //bar title 
                    @DrawableRes val leftRes: Int = R.drawable.back,            // bar 左边icon的资源
                    @DrawableRes val rightRes: Int = R.drawable.icon_sure,      // bar 右边icon的资源
                    val leftText: String = "",                                  //目前无效
                    val rightText: String = "",                                 //目前无效
                    @StringRes val titleRes: Int = -1,                          //bar title res, 如果设置，会覆盖 title字段
                    @ColorRes val barBgColor: Int = R.color.colorPrimary){      //bar 的背景颜色
}
```
