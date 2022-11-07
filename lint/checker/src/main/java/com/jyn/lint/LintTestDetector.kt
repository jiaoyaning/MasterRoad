package com.jyn.lint

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression
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
        return Collections.singletonList("tag")
    }

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        if (!context.evaluator.isMemberInClass(method, "com.apkfuns.logutils.LogUtils")) {
            return
        }

        println("Detector -> ")

        println("node.typeArguments -> ${node.typeArguments}")
        println("node.typeArguments -> ${node.kind}")
        println("node.methodName -> ${node.methodName}")
        println("node.valueArguments -> ${node.valueArguments}")
        println("context:$context ,node:$node , method:$method")
    }
}