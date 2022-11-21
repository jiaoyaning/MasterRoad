package com.jyn.lint

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiMethodCallExpression
import com.intellij.psi.PsiReferenceExpression
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

        println("Detector -> ")

        node.valueArguments[0].sourcePsi?.children?.forEach { psiElement ->
            print(" " + psiElement.text)

            when (psiElement) {
                is PsiMethodCallExpression -> {
                    print(" -> java方法")
                }
                is PsiReferenceExpression -> {
                    print(" -> java变量")
                }
            }

            psiElement.toUElement()?.let { uElement ->
                when (uElement) {
                    is ULiteralExpression -> {
                        print(" -> 字符串 ")
                    }
                    is UCallExpression -> {
                        print(" -> 方法 ")
                        val text = uElement.resolveToUElement()?.sourcePsi?.text
                        print(text)
                    }
                    is UReferenceExpression -> {
                        print(" -> 变量 ")
                        val text = uElement.resolve()?.text
                        print(text)
                    }
                    else -> {}
                }
            }
            println()
        }

//        println("node.typeArguments -> ${node.typeArguments}")
//        println("node.typeArguments -> ${node.kind}")
//        println("node.methodName -> ${node.methodName}")
//        println("node.valueArguments -> ${node.valueArguments}")
//        println("context:$context ,node:$node , method:$method")
    }
}