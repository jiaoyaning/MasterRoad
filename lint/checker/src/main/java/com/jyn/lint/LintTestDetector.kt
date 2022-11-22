package com.jyn.lint

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.*
import java.util.*

class LintTestDetector : Detector(), Detector.UastScanner {
    companion object {
        const val DEBUG = true

        val ISSUE = Issue.create(
            "TestDetectorError",
            "这是一个测试Detector的描述",
            "这是一个测试Detector的解释",
            Category.CORRECTNESS,
            8,
            Severity.ERROR,
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

        //遍历参数体
        node.valueArguments.last().sourcePsi?.children?.forEach(::resolvePsiElement)
    }

    /**
     * PsiElement 类型判断
     */
    private fun resolvePsiElement(psiElement: PsiElement?) {
        // psi 转 UAST
        psiElement?.toUElement()?.let {
            when (it) {
                is ULiteralExpression -> { //字符串类型
                    log("字符串 -> ")
                    checkLiteral(it)
                }
                is UCallExpression -> { //方法类型
                    log("方法 -> ")
                    resolveCall(it)
                }
                is UReferenceExpression -> { //变量类型
                    log("变量 -> ")
                    resolveVariable(it)
                }
                else -> {
                    log("${it.javaClass.simpleName} ->" + it.sourcePsi?.text)
                }
            }
            log()
        }
    }

    /**
     * 检查字符串类型是否涉及隐私数据
     */
    private fun checkLiteral(uLiteral: ULiteralExpression): Boolean {
        log(uLiteral.sourcePsi?.text)
        return true
    }

    /**
     * 回溯方法类型
     */
    private fun resolveCall(uCall: UCallExpression): Boolean {
        val uElement: UElement? = uCall.resolveToUElement() //回溯至方法定义时UElement
        val sourcePsi = uElement?.sourcePsi
        log(sourcePsi?.text)
        return true
    }

    /**
     * 回溯变量类型
     */
    private fun resolveVariable(uReference: UReferenceExpression): Boolean {
        val uElement = uReference.resolveToUElement() //回溯至变量初始化时UElement
        log("【${uElement?.sourcePsi?.text}】 -> ")

        //判断变量值类型
        when (uElement) {
            is UField -> {
                log("全局变量 -> ")
                val initSourcePsi = uElement.uastInitializer?.sourcePsi //返回变量的初始化值内容
                log("提取Value值 -> ")
                resolvePsiElement(initSourcePsi)
            }
            is ULocalVariable -> {
                log("局部变量 -> ")
                val initSourcePsi = uElement.uastInitializer?.sourcePsi //返回变量的初始化值内容
                log("提取Value值 -> ")
                resolvePsiElement(initSourcePsi)
            }
            is UParameter -> {
                log("形参 -> ${uElement.sourcePsi?.text} -> ")
            }
        }

//        log()
//        val resolveLastChild: PsiElement? = sourcePsi?.lastChild
        return true
    }

    private fun log(msg: String? = null) {
        if (!DEBUG) return
        msg?.let { print(it) } ?: println()
    }
}