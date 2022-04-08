package com.fphoenixcorneae.widget

import android.animation.TypeEvaluator
import java.math.BigDecimal

/**
 * @desc：BigDecimalEvaluator
 * @date：2022/04/08 11:03
 */
class BigDecimalEvaluator : TypeEvaluator<BigDecimal> {

    override fun evaluate(fraction: Float, startValue: BigDecimal, endValue: BigDecimal): BigDecimal {
        val result = endValue.subtract(startValue)
        return result.multiply(BigDecimal(fraction.toDouble())).add(startValue)
    }
}