package com.jyn.lint

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.*
import java.util.*
import javax.annotation.RegEx

class LintTestDetector : Detector(), Detector.UastScanner {
    companion object {
        const val MAX_COUNT = 3 //最大回溯次数
        const val DEBUG = true

        val ISSUE = Issue.create(
            "TestDetectorError",
            "这是一个测试Lint",
            "这是一个测试Lint",
            Category.CORRECTNESS,
            8,
            Severity.WARNING,
            Implementation(LintTestDetector::class.java, Scope.JAVA_FILE_SCOPE)
        )
    }

    var node: UElement? = null

    override fun getApplicableMethodNames(): List<String> {
        return Collections.singletonList("log")
    }

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        if (!context.evaluator.isMemberInClass(method, "com.jyn.common.Utils.MLog")) {
            return
        }
        ReportUtil.context = context

        sout("\n-----------------------------------\n\n")

        //遍历参数体
        node.valueArguments.last().sourcePsi
            ?.apply { sout("原文 -> ${this.text}\n") }
            ?.children
            ?.forEach {
                this.node = node
                resolvePsiElement(it, 0)
            }
    }

    /**
     * PsiElement 类型判断
     */
    private fun resolvePsiElement(psiElement: PsiElement?, count: Int) {
        if (psiElement == null ||
            psiElement.text.isNullOrBlank() ||
            psiElement.toUElement() == null ||
            count > (MAX_COUNT - 1) //从零开始计算，因此减一
        ) {
            return
        }

        sout(" 【${psiElement.text}】 -> ")

        // psi 转 UAST
        psiElement.toUElement().let {
            when (it) {
                is ULiteralExpression -> { //文字值类型，如数字、布尔值和字符串。
                    sout("文字值 -> ")
                    checkLiteral(it, count)
                }
                is UCallExpression -> { //方法类型
                    sout("方法 $count 方法名 -> ")
                    val isHit = check(it.methodName)
                    if (isHit) return
                    resolveCall(it, count + 1)
                }
                is UQualifiedReferenceExpression,
                is USimpleNameReferenceExpression -> { //变量类型
                    sout("变量 $count 变量名 -> ")
                    val isHit = check(it.sourcePsi?.text)
                    if (isHit) return
                    resolveVariable(it as UReferenceExpression, count + 1)
                }
                else -> {
                    sout("未知 -> ${it?.javaClass?.simpleName} ->" + it?.sourcePsi?.text)
                }
            }
        }
        if (count == 0) sout()
    }

    /**
     * 检查文字值类型
     * 如数字、布尔值和字符串
     */
    private fun checkLiteral(uLiteral: ULiteralExpression, count: Int): Boolean {
        sout("END")
        return false
    }

    /**
     * 回溯方法类型
     */
    private fun resolveCall(uCall: UCallExpression, count: Int): Boolean {
        val uElement: UElement? = uCall.resolveToUElement() //回溯至方法定义处UElement
        sout(" \n\t${uElement?.sourcePsi?.text}\n")
        if (uElement is UMethod) {
            //获取方法体
            val uastBody = uElement.uastBody
            if (uastBody is UBlockExpression) {
                //获取最后一行表达式
                val last = uastBody.expressions.last()
                // 判断表达式是否为return值类型
                if (last is UReturnExpression) {
                    //获取return值
                    val returnExpression = last.returnExpression
                    sout(" return值 ->")
                    //回溯return值
                    resolvePsiElement(returnExpression?.sourcePsi, count)
                }
            }
        }
        return false
    }

    /**
     * 回溯变量类型
     */
    private fun resolveVariable(uReference: UReferenceExpression, count: Int): Boolean {
        val uElement = uReference.resolveToUElement() //回溯至变量初始化时UElement
        if (uElement?.sourcePsi?.text.isNullOrBlank()) return false
        sout("初始值【${uElement?.sourcePsi?.text}】-> ")

        //判断变量值类型
        when (uElement) {
            is UField,
            is ULocalVariable -> {
                sout("变量 ->")
                val initSourcePsi = (uElement as UVariable).uastInitializer?.sourcePsi //返回变量的初始化值内容
                initSourcePsi
                    ?.let { resolvePsiElement(initSourcePsi, count) }
                    ?: sout("初始值NULL")
            }
            is UParameter -> {
                sout("形参 -> ")
                sout("${uElement.text} ->")

                //提取方法体
                val uMethod = uReference.getParentOfType(UMethod::class.java, false)

                //获取形参index
//                uElement.parameterIndex() // 该方法不知道为什么会报错，因此换成通过字符串匹配来获取index
                val index = uMethod?.parameterList?.parameters
                    ?.indexOfFirst {
                        it.text.equals(uElement.text)
                    }
                sout("方法名：${uMethod?.name}, index：${index} ")
//                val references = uMethod?.references // TODO 方法调用处回溯一直为null，占未找到解决方法
            }
        }
        return false
    }

    private fun check(target: String?): Boolean {
        sout(" check【$target】 ->")
        if (target.isNullOrBlank()) return false
        val isMatch = Regex("chat.?id|user.?id", RegexOption.IGNORE_CASE).containsMatchIn(target)
        if (isMatch) {
            sout("【==> 匹配成功 <==】")
            ReportUtil.report(ISSUE, this.node, "$target -> Log有问题哦，请改正！！！")
            return true
        }
        return false
    }

    private fun sout(msg: String? = null) {
        if (!DEBUG) return
        msg?.let { print(it) } ?: println()
    }
}