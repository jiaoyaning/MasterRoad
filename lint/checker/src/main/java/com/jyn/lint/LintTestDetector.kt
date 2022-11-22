package com.jyn.lint

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiMethodCallExpression
import com.intellij.psi.PsiReferenceExpression
import org.jetbrains.kotlin.psi.KtParameter
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.psi.psiUtil.isFunctionalExpression
import org.jetbrains.uast.*
import org.jetbrains.uast.generate.refreshed
import org.jetbrains.uast.generate.shortenReference
import java.util.*

class LintTestDetector : Detector(), Detector.UastScanner {
    companion object {
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
        node.valueArguments[0].sourcePsi?.children?.forEach {
            resolvePsiElement(it)
            println()
        }
    }

    /**
     * PsiElement 类型判断
     */
    private fun resolvePsiElement(psiElement: PsiElement?) {
        when (val uElement = psiElement?.toUElement()) { // psi 转 UAST
            is ULiteralExpression -> {
                println(" -> 字符串 -> " + uElement.sourcePsi?.text)
            }
            is UCallExpression -> {
                print(" -> 方法 -> ")
                resolveCall(uElement)
            }
            is UReferenceExpression -> {
                print(" -> 变量 -> ")
                resolveVariable(uElement)
            }
            else -> {
                println(" -> ${uElement?.javaClass?.simpleName} :" + uElement?.sourcePsi?.text)
            }
        }
    }

    /**
     * 回溯方法
     */
    private fun resolveCall(uCall: UCallExpression): Boolean {
        val uElement: UElement? = uCall.resolveToUElement() //回溯至方法定义时UElement
        val sourcePsi = uElement?.sourcePsi
        println(sourcePsi?.text)
        return true
    }

    /**
     * 回溯变量
     */
    private fun resolveVariable(uReference: UReferenceExpression): Boolean {
        val uElement = uReference.resolveToUElement() //回溯至变量初始化时UElement
        val sourcePsi = uElement?.sourcePsi

        //判断变量值类型
        if (sourcePsi is KtProperty) {  //kotlin属性类型，包含局部变量
            print(" 变量 -> ")
        } else if (sourcePsi is KtParameter) { //kotlin形参类型
            print(" 参数 -> ")
        }
        println(sourcePsi?.text)
        val resolveLastChild: PsiElement? = sourcePsi?.lastChild
        return true
    }
}