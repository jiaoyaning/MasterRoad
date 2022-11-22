package com.jyn.lint

import com.android.tools.lint.detector.api.*
import com.intellij.psi.*
import org.jetbrains.uast.*
import org.jetbrains.uast.util.isAssignment
import org.jetbrains.uast.util.isMethodCall
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
        node.valueArguments[0].sourcePsi?.children?.forEach(::resolvePsiElement)
    }

    /**
     * PsiElement 类型判断
     */
    private fun resolvePsiElement(psiElement: PsiElement?) {
        // psi 转 UAST
        psiElement?.toUElement()?.let {
            when (it) {
                is ULiteralExpression -> { //字符串类型
                    print("字符串 -> ")
                    checkLiteral(it)
                }
                is UCallExpression -> { //方法类型
                    print("方法 -> ")
                    resolveCall(it)
                }
                is UReferenceExpression -> { //变量类型
                    print("变量 -> ")
                    resolveVariable(it)
                }
                else -> {
                    println("${it.javaClass.simpleName} ->" + it.sourcePsi?.text)
                }
            }
            println()
        }
    }

    /**
     * 检查字符串类型是否涉及隐私数据
     */
    private fun checkLiteral(uLiteral: ULiteralExpression): Boolean {
        println(uLiteral.sourcePsi?.text)
        return true
    }

    /**
     * 回溯方法类型
     */
    private fun resolveCall(uCall: UCallExpression): Boolean {
        val uElement: UElement? = uCall.resolveToUElement() //回溯至方法定义时UElement
        val sourcePsi = uElement?.sourcePsi
        println(sourcePsi?.text)
        return true
    }

    /**
     * 回溯变量类型
     */
    private fun resolveVariable(uReference: UReferenceExpression): Boolean {
        val uElement = uReference.resolveToUElement() //回溯至变量初始化时UElement
        print("【${uElement?.sourcePsi?.text}】 -> ")

        //判断变量值类型
        when (uElement) {
            is UField -> {
                print("全局变量 -> ")
                val initSourcePsi = uElement.uastInitializer?.sourcePsi //返回变量的初始化值内容
                print("提取Value值 -> ")
                resolvePsiElement(initSourcePsi)
            }
            is ULocalVariable -> {
                print("局部变量 -> ")
                val initSourcePsi = uElement.uastInitializer?.sourcePsi //返回变量的初始化值内容
                print("提取Value值 -> ")
                resolvePsiElement(initSourcePsi)
            }
            is UParameter -> {
                print("形参 -> ${uElement.sourcePsi?.text} -> ")
            }
        }

        //判断变量值类型
//        when (sourcePsi) {
//            is KtProperty -> {  //kotlin属性类型，包含局部变量
//                print(" kotlin 变量 -> ")
//            }
//            is KtParameter -> { //kotlin形参类型
//                print(" kotlin 形参 -> ")
//            }
//            is PsiVariable -> { //Java 全局变量PsiField & 局部变量PsiLocalVariable
//                print(" java 变量 -> ")
//            }
//            is PsiParameter -> {
//                print(" java 形参 -> ")
//            }
//        }
        println()
//        val resolveLastChild: PsiElement? = sourcePsi?.lastChild
        return true
    }
}