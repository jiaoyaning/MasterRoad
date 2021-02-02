package com.jyn.masterroad.ConstraintLayout

import android.widget.SeekBar
import androidx.databinding.BindingAdapter

/**
 * 自定义DataBindingAdapter
 *
 * 官方文档
 * https://developer.android.com/topic/libraries/data-binding/binding-adapters#samples
 */
@BindingAdapter("onSeekBarChangeListener")
fun setOnSeekBarChangeListener(seekBar: SeekBar, onSeekBarChangeListener: SeekBar.OnSeekBarChangeListener) {
    seekBar.setOnSeekBarChangeListener(onSeekBarChangeListener)
}