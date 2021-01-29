package com.jyn.masterroad.ConstraintLayout

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.masterroad.R
import com.jyn.masterroad.base.RoutePath
import com.jyn.masterroad.databinding.ActivityConstraintlayoutBinding

/**
 * https://juejin.cn/post/6854573221312725000
 *
 * http://www.flyou.ren/2019/10/10/ConstraintLayout/
 *
 * https://mp.weixin.qq.com/s/7wEr6okR-CAAoNDYB4gHig
 *
 * https://juejin.cn/post/6844903733948579847
 *
 * https://mp.weixin.qq.com/s/Z_TnoyMRYZEQXvlqiKX8Uw
 *
 * https://www.paincker.com/constraint-layout
 */
@Route(path = RoutePath.ConstraintLayout.path)
class ConstraintLayoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConstraintlayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConstraintlayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.onClick = click
    }

    var click = View.OnClickListener {
        when (it.id) {
            R.id.tv_space_test -> {
                switchSpaceBias()
            }
            R.id.btn_chain_style_spread -> {
                switchChainStyle(ConstraintLayout.LayoutParams.CHAIN_SPREAD)
            }
            R.id.btn_chain_style_spread_inside -> {
                switchChainStyle(ConstraintLayout.LayoutParams.CHAIN_SPREAD_INSIDE)
            }
            R.id.btn_chain_style_packed -> {
                switchChainStyle(ConstraintLayout.LayoutParams.CHAIN_PACKED)
            }
        }
    }

    /**
     * 动态切换Space相对位置
     */
    @SuppressLint("SetTextI18n")
    private fun switchSpaceBias() {
        val layoutParams = binding.space.layoutParams as ConstraintLayout.LayoutParams
        val bias = if (layoutParams.horizontalBias != 0.5f) 0.5f else 0.8f
        layoutParams.horizontalBias = bias
        layoutParams.verticalBias = bias
        binding.space.layoutParams = layoutParams
        binding.tvSpaceTest.text = "相对位置 ${bias * 100}%"
    }

    /**
     * 动态修改链的style
     */
    private fun switchChainStyle(style: Int) {
        val layoutParams = binding.btnChainStyleSpread.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.horizontalChainStyle = style
        binding.btnChainStyleSpread.layoutParams = layoutParams
    }
}