# AnimatedNumberTextView

## 数字增加或减少动画的 TextView

[![](https://jitpack.io/v/FPhoenixCorneaE/AnimatedNumberTextView.svg)](https://jitpack.io/#FPhoenixCorneaE/AnimatedNumberTextView)

![](https://github.com/FPhoenixCorneaE/AnimatedNumberTextView/blob/main/images/animated-number-text.gif)

### How to include it in your project:

**Step 1.** Add the JitPack repository to your build file.

```groovy
allprojects {
    repositories {
        google()
        mavenCentral()
        maven {
            url "https://jitpack.io"
        }
    }
}
```

**Step 2.** Add the dependency.

```groovy
dependencies {
    implementation("com.github.FPhoenixCorneaE:AnimatedNumberTextView:$latest")
}
```

### 特性

1. ##### 可添加前缀、后缀
2. ##### 支持整数、小数
3. ##### 可添加千位分隔符

### xml中使用

```xml

<com.fphoenixcorneae.widget.AnimatedNumberTextView android:id="@+id/tvAnimatedNumber1"
    android:layout_width="wrap_content" android:layout_height="wrap_content" android:layout_marginTop="50dp"
    android:textColor="@color/black" android:textSize="28sp" android:textStyle="bold"
    app:animatedNumberAnimEnable="true" app:animatedNumberDuration="2000" app:animatedNumberEnd="999999999.99999"
    app:animatedNumberPrefix="¥" app:animatedNumberStart="0" app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintStart_toStartOf="parent" app:layout_constraintTop_toTopOf="parent" />
```

### 代码中使用

```kotlin
findViewById<AnimatedNumberTextView>(R.id.tvAnimatedNumber4).apply {
    numberStart = "0"
    numberEnd = "123456789"
    numberAnimEnable = true
    numberDuration = 2000
    numberPrefix = "-----"
    numberPostfix = "-----"
    numberFormatEnable = false
    start()
}
```

参考：https://github.com/Bakumon/NumberAnimTextView
