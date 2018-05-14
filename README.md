[![](https://jitpack.io/v/SunnyQjm/DCIMDemo.svg)](https://jitpack.io/#SunnyQjm/DCIMDemo)
# DCIMDemo
这是一个简单的图片选择器

## 效果图
- ### 单选
  
```kotlin
 EasyDCIM.with(this) // 需要一个Activity对象                 
          .setMode(EasyDCIM.MODE_SELECT_SINGLE)   //设置模式：多选/单选
          .setSaveState(false)                    //设置是否保存上一次的选择状态
          .setEasyBarParam(EasyBarParams(titleRes = R.string.title, barBgColor = R.color.colorAccent,
                  rightRes = R.drawable.cat))     //设置相册选择器中Bar的样式
          .jumpForResult(0)                       //传入一个requestCode
```
