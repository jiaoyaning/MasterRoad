package com.jyn.lint

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiReferenceService
import com.intellij.psi.util.PsiTreeUtil
import org.jetbrains.kotlin.utils.sure
import org.jetbrains.uast.*
import java.util.*

class LintTestDetector : Detector(), Detector.UastScanner {
    companion object {
        const val MAX_COUNT = 3 //最大回溯次数
        const val DEBUG = true

        val ISSUE = Issue.create(
            "TestDetectorError",
            "这是一个测试Detector的描述",
            "这是一个测试Detector的解释",
            Category.CORRECTNESS,
            8,
            Severity.WARNING,
            Implementation(LintTestDetector::class.java, Scope.JAVA_FILE_SCOPE)
        )
    }

    override fun getApplicableMethodNames(): List<String> {
        return Collections.singletonList("log")
    }

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        if (!context.evaluator.isMemberInClass(method, "com.jyn.common.Utils.MLog")) {
            return
        }

        sout("\n-----------------------------------\n\n")

        //遍历参数体
        node.valueArguments.last()
            .sourcePsi?.apply { sout("原文 -> ${this.text}\n") }
            ?.children?.forEach { resolvePsiElement(it, 0) }
        sout()
    }

    /**
     * PsiElement 类型判断
     */
    private fun resolvePsiElement(psiElement: PsiElement?, count: Int) {
        if (psiElement == null || psiElement.text.isNullOrBlank() || psiElement.toUElement() == null) return

        sout(" Count:$count【${psiElement.text}】 -> ")

        // psi 转 UAST
        psiElement.toUElement().let {
            when (it) {
                is ULiteralExpression -> { //文字值类型，如数字、布尔值和字符串。
                    sout("文字值 -> ")
                    checkLiteral(it, count)
                    return
                }
                is UCallExpression -> { //方法类型
                    sout("方法 -> ")
                    resolveCall(it, count)
                }
                is USimpleNameReferenceExpression -> { //变量类型
                    sout("变量 -> ")
                    resolveVariable(it, count)
                }
                is UQualifiedReferenceExpression -> { //对象属性
                    sout("对象属性 -> ")
                    resolveVariable(it, count)
                }
                else -> {
                    sout("未知 -> ")
                    sout("${it?.javaClass?.simpleName} ->" + it?.sourcePsi?.text)
                }
            }
        }
    }

    /**
     * 检查文字值类型
     * 如数字、布尔值和字符串
     */
    private fun checkLiteral(uLiteral: ULiteralExpression, count: Int): Boolean {
        sout("END \n")
        return false
    }

    /**
     * 回溯方法类型
     */
    private fun resolveCall(uCall: UCallExpression, count: Int): Boolean {
        val uElement: UElement? = uCall.resolveToUElement() //回溯至方法定义时UElement
        sout(" \n\t${uElement?.sourcePsi?.text}\n")
        if (uElement is UMethod) {
            val uastBody = uElement.uastBody
            if (uastBody is UBlockExpression) {
                val last = uastBody.expressions.last()
                if (last is UReturnExpression) {
                    val returnExpression: UExpression? = last.returnExpression
                    sout(" return值 -> ${returnExpression?.sourcePsi?.text} ->")
                    resolvePsiElement(returnExpression?.sourcePsi, count + 1)
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
        sout("初始值【${uElement?.sourcePsi?.text}】 -> ")

        //判断变量值类型
        when (uElement) {
            is UField,
            is ULocalVariable -> {
                sout("变量 -> ")
                val initSourcePsi = (uElement as UVariable).uastInitializer?.sourcePsi //返回变量的初始化值内容
                resolvePsiElement(initSourcePsi, count + 1)
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
            }
        }
        return false
    }

    private fun sout(msg: String? = null) {
        if (!DEBUG) return
        msg?.let { print(it) } ?: println()
    }
}