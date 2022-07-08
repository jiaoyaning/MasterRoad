package com.jyn.masterroad.nestedscrolling.constraintLayout

import android.annotation.SuppressLint
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.adapters.SeekBarBindingAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.common.Base.BaseActivity
import com.jyn.masterroad.databinding.ActivityConstraintlayoutBinding

/*
 * 官方文档：
 * https://developer.android.google.cn/training/constraint-layout
 *
 * ConstraintLayout 2.0 新特性详解及实战
 * https://blog.csdn.net/weixin_34677811/article/details/90719945
 *
 * ConstraintLayout2.0进阶之路
 * https://mp.weixin.qq.com/s/wXTujdVSlb3KsJpDRU6Nog
 */
@SuppressLint("SetTextI18n")
@Route(path = RoutePath.ConstraintLayout.path)
class ConstraintLayoutActivity : BaseActivity<ActivityConstraintlayoutBinding>
    (R.layout.activity_constraintlayout) {

    override fun initView() {
        binding.onClick = click
        binding.onProgressChanged = onProgressChanged
    }

    private val onProgressChanged = SeekBarBindingAdapter.OnProgressChanged { seekBar, progress, _ ->
        when (seekBar) {
            binding.biasSeekBarTest -> changeBias(progress)
            binding.clGuideline.guidelineSeekBar -> binding.clGuideline.guideline.setGuidelinePercent(progress / 100f)
//            binding.clFlow.flowSeekBarHorizontalBias -> binding.clFlow.clFlow.setHorizontalBias(progress / 100f)
        }
    }

    private var click = View.OnClickListener {
        when (it.id) {
            R.id.tv_space_test -> switchSpaceBias()                 //space 可用来当约束锚点

            R.id.btn_chain_style_spread,
            R.id.btn_chain_style_packed,
            R.id.btn_chain_style_spread_inside -> switchChainStyle(it.id)  //chain 链式布局

            R.id.btn_gone_margin_test -> switchGoneMargin()         // goneMargin 属性测试
            R.id.btn_barrier_test -> switchBarrierTest()            //barrier 内容屏障测试

            R.id.btn_group_control_all,
            R.id.btn_group_control_myself -> controlGroup(it.id)       //Group 组控制

            R.id.btn_placeholder -> switchPlaceholder()

//            R.id.btn_flow_wrap_mode_none,
//            R.id.btn_flow_wrap_mode_chain,
//            R.id.btn_flow_wrap_mode_aligned,
//            R.id.btn_flow_max_elements_wrap3,
//            R.id.btn_flow_max_elements_wrap1,
//            R.id.btn_flow_horizontal_style_spread,
//            R.id.btn_flow_horizontal_style_packed,
//            R.id.btn_flow_horizontal_style_spread_inside -> switchFlow(it.id)     //flow 测试
        }
    }

    private fun controlGroup(id: Int) {
        if (id == R.id.btn_group_control_all) {
            if (binding.clGroup.group.visibility == View.VISIBLE) binding.clGroup.group.visibility = View.GONE
            else binding.clGroup.group.visibility = View.VISIBLE
        } else {
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
            R.id.btn_chain_style_spread -> layoutParams.horizontalChainStyle = ConstraintLayout.LayoutParams.CHAIN_SPREAD
            R.id.btn_chain_style_spread_inside -> layoutParams.horizontalChainStyle = ConstraintLayout.LayoutParams.CHAIN_SPREAD_INSIDE
            R.id.btn_chain_style_packed -> layoutParams.horizontalChainStyle = ConstraintLayout.LayoutParams.CHAIN_PACKED
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

    /**
     * 切换到占位
     */
    private fun switchPlaceholder() {
        binding.clPlaceholder.placeholder.setContentId(R.id.btn_placeholder)
    }

//    private fun switchFlow(id: Int) {
//        when (id) {
//            R.id.btn_flow_wrap_mode_none -> binding.clFlow.clFlow.setWrapMode(Flow.WRAP_NONE)
//            R.id.btn_flow_wrap_mode_chain -> binding.clFlow.clFlow.setWrapMode(Flow.WRAP_CHAIN)
//            R.id.btn_flow_wrap_mode_aligned -> binding.clFlow.clFlow.setWrapMode(Flow.WRAP_ALIGNED)
//            R.id.btn_flow_max_elements_wrap3 -> binding.clFlow.clFlow.setMaxElementsWrap(3)
//            R.id.btn_flow_max_elements_wrap1 -> binding.clFlow.clFlow.setMaxElementsWrap(1)
//            R.id.btn_flow_horizontal_style_packed -> binding.clFlow.clFlow.setHorizontalStyle(Flow.CHAIN_PACKED)
//            R.id.btn_flow_horizontal_style_spread -> binding.clFlow.clFlow.setHorizontalStyle(Flow.CHAIN_SPREAD)
//            R.id.btn_flow_horizontal_style_spread_inside -> binding.clFlow.clFlow.setHorizontalStyle(Flow.CHAIN_SPREAD_INSIDE)
//        }
//    }

    /**
     * 获取view的LayoutParams
     */
    private fun getLayoutParams(view: View): ConstraintLayout.LayoutParams {
        return view.layoutParams as ConstraintLayout.LayoutParams
    }
}