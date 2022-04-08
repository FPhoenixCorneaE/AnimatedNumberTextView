package com.fphoenixcorneae.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.widget.AppCompatTextView
import java.math.BigDecimal
import java.text.DecimalFormat

/**
 * @desc：数字增加或减少动画的 TextView
 * @date：2022/04/08 10:27
 */
class AnimatedNumberTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AppCompatTextView(context, attrs, defStyleAttr) {

    /** 起始值 默认 "0" */
    var numberStart: String? = "0"

    /** 结束值 */
    var numberEnd: String? = null

    /** 动画总时间 默认 2000 毫秒 */
    var numberDuration: Long = 2000

    /** 前缀 */
    var numberPrefix: String? = ""

    /** 后缀 */
    var numberPostfix: String? = ""

    /** 是否开启动画 */
    var numberAnimEnable = true

    /** 是否格式化 */
    var numberFormatEnable = true

    /** 是否是整数 */
    private var mIsInt = false

    /** 数字动画 */
    private var mNumberAnimator: ValueAnimator? = null

    init {
        initAttrs(context, attrs, defStyleAttr)

        start()
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.AnimatedNumberTextView, defStyleAttr, 0)
        runCatching {
            numberStart = typedArray.getString(R.styleable.AnimatedNumberTextView_animatedNumberStart) ?: "0"
            numberEnd = typedArray.getString(R.styleable.AnimatedNumberTextView_animatedNumberEnd)
            numberDuration = typedArray.getInt(R.styleable.AnimatedNumberTextView_animatedNumberDuration, 2000).toLong()
            numberPrefix = typedArray.getString(R.styleable.AnimatedNumberTextView_animatedNumberPrefix) ?: ""
            numberPostfix = typedArray.getString(R.styleable.AnimatedNumberTextView_animatedNumberPostfix) ?: ""
            numberAnimEnable = typedArray.getBoolean(R.styleable.AnimatedNumberTextView_animatedNumberAnimEnable, true)
            numberFormatEnable =
                typedArray.getBoolean(R.styleable.AnimatedNumberTextView_animatedNumberFormatEnable, true)
        }
        typedArray.recycle()
    }

    /**
     * 校验数字的合法性
     *
     * @param numberStart 　开始的数字
     * @param numberEnd   　结束的数字
     * @return 合法性
     */
    private fun checkNumberString(numberStart: String, numberEnd: String): Boolean {
        val regexInteger = "-?\\d*".toRegex()
        mIsInt = numberStart.matches(regexInteger) && numberEnd.matches(regexInteger)
        if (mIsInt) {
            return true
        }
        val regexDecimal = "-?[1-9]\\d*.\\d*|-?0.\\d*[1-9]\\d*".toRegex()
        return numberEnd.matches(regexDecimal) && ("0" == numberStart || numberStart.matches(regexDecimal))
    }

    /**
     * 开始动画
     */
    @SuppressLint("SetTextI18n")
    fun start() {
        if (numberStart == null || numberEnd == null) {
            return
        }
        if (!checkNumberString(numberStart!!, numberEnd!!)) {
            // 数字不合法　直接调用setText()设置最终值
            text = numberPrefix + numberEnd + numberPostfix
            return
        }
        if (!numberAnimEnable) {
            // 禁止动画
            text = numberPrefix + format(BigDecimal(numberEnd)) + numberPostfix
            return
        }
        mNumberAnimator = ValueAnimator.ofObject(BigDecimalEvaluator(), BigDecimal(numberStart), BigDecimal(numberEnd))
            .apply {
                duration = numberDuration
                interpolator = AccelerateDecelerateInterpolator()
                addUpdateListener { valueAnimator ->
                    val value = valueAnimator.animatedValue as BigDecimal
                    text = numberPrefix + format(value) + numberPostfix
                }
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        text = numberPrefix + format(BigDecimal(numberEnd)) + numberPostfix
                    }
                })
                start()
            }
    }

    /**
     * 格式化 BigDecimal ,小数部分时保留两位小数并四舍五入
     *
     * @param bd 　BigDecimal
     * @return 格式化后的 String
     */
    private fun format(bd: BigDecimal): String? {
        if (numberStart == null || numberEnd == null) {
            return null
        }
        val pattern = StringBuilder()
        if (mIsInt) {
            pattern.append(if (numberFormatEnable) "#,###" else "####")
        } else {
            pattern.append(if (numberFormatEnable) "#,##0" else "###0")
            var length = 0
            val s1 = numberStart!!.split("\\.".toRegex()).toTypedArray()
            val s2 = numberEnd!!.split("\\.".toRegex()).toTypedArray()
            val s = if (s1.size > s2.size) s1 else s2
            if (s.size > 1) {
                // 小数部分
                val decimals = s[1]
                length = decimals.length
            }
            if (length > 0) {
                pattern.append(".")
                for (i in 0 until length) {
                    pattern.append("0")
                }
            }
        }
        val df = DecimalFormat(pattern.toString())
        return df.format(bd)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mNumberAnimator?.cancel()
    }
}