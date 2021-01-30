package com.jyn.masterroad.ConstraintLayout

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
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
@SuppressLint("SetTextI18n")
@Route(path = RoutePath.ConstraintLayout.path)
class ConstraintLayoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConstraintlayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConstraintlayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.onClick = click
        initView()
    }

    private fun initView() {
        binding.biasSeekBarTest.setOnSeekBarChangeListener(seekBarChange)
    }

    private val seekBarChange = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            when (seekBar) {
                binding.biasSeekBarTest -> {
                    changeBias(progress)
                }
            }
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {

        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {

        }
    }

    private var click = View.OnClickListener {
        when (it.id) {
            R.id.tv_space_test -> switchSpaceBias()                 //space 可用来当约束锚点

            R.id.btn_chain_style_spread,
            R.id.btn_chain_style_spread_inside,
            R.id.btn_chain_style_packed -> switchChainStyle(it.id)  //chain 链式布局

            R.id.btn_gone_margin_test -> switchGoneMargin()         // goneMargin 属性测试
            R.id.btn_barrier_test -> switchBarrierTest()            //barrier 内容屏障测试

            R.id.btn_guideline_left,
            R.id.btn_guideline_right -> removeGuideline(it.id)      //guideline 移动基准线

            R.id.btn_group_control_myself,
            R.id.btn_group_control_all -> controlGroup(it.id)       //Group 组控制
        }
    }

    private fun controlGroup(id: Int) {
        if (id == R.id.btn_group_control_all) {
            if (binding.clGroup.group.visibility == View.VISIBLE) binding.clGroup.group.visibility = View.GONE
            else binding.clGroup.group.visibility = View.VISIBLE
        }else {
            if (binding.clGroup.btnGroupControlMyself.visibility == View.VISIBLE) binding.clGroup.btnGroupControlMyself.visibility = View.GONE
            else binding.clGroup.btnGroupControlMyself.visibility = View.VISIBLE
        }
    }

    /**
     * 动态切换Space相对位置
     */
    private fun switchSpaceBias() {
        val layoutParams = getLayoutParams(binding.space)
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
        val layoutParams = getLayoutParams(binding.btnChainStyleSpread)
        when (style) {
            R.id.btn_chain_style_spread -> {
                layoutParams.horizontalChainStyle = ConstraintLayout.LayoutParams.CHAIN_SPREAD
            }
            R.id.btn_chain_style_spread_inside -> {
                layoutParams.horizontalChainStyle = ConstraintLayout.LayoutParams.CHAIN_SPREAD_INSIDE
            }
            R.id.btn_chain_style_packed -> {
                layoutParams.horizontalChainStyle = ConstraintLayout.LayoutParams.CHAIN_PACKED
            }
        }

        binding.btnChainStyleSpread.layoutParams = layoutParams
    }

    /**
     * goneMargin 属性测试
     */
    private fun switchGoneMargin() {
        val isVisibility = binding.btnGoneMarginAnchor.visibility == View.VISIBLE
        binding.btnGoneMarginAnchor.visibility = if (isVisibility) View.GONE else View.VISIBLE
    }

    /**
     * 改动Bias值
     */
    private fun changeBias(progress: Int) {
        val layoutParams = getLayoutParams(binding.biasShowTest)
        val bias = progress / 100f
        layoutParams.horizontalBias = bias
        binding.biasShowTest.layoutParams = layoutParams
        binding.biasShowTest.text = "bias:${bias}"
    }

    private fun switchBarrierTest() {
        if (binding.clBarrier.tvBarrierTest1.text.length > 5) {
            binding.clBarrier.tvBarrierTest1.text = "我变短了"
            binding.clBarrier.tvBarrierTest2.text = "我变得特别特别特别长"
        } else {
            binding.clBarrier.tvBarrierTest1.text = "这是一个很长很长很长的文本"
            binding.clBarrier.tvBarrierTest2.text = "很短"
        }
    }

    private fun removeGuideline(id: Int) {
        val guidePercent = if (id == R.id.btn_guideline_left) 0.3f else 0.5f
        binding.clGuideline.guideline.setGuidelinePercent(guidePercent)
    }


    /**
     * 获取view的LayoutParams
     */
    private fun getLayoutParams(view: View): ConstraintLayout.LayoutParams {
        return view.layoutParams as ConstraintLayout.LayoutParams
    }
}