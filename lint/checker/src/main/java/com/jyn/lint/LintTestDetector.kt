package com.jyn.lint

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import com.intellij.psi.JavaElementVisitor
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiMethodCallExpression
import org.jetbrains.uast.*
import org.jetbrains.uast.visitor.AbstractUastVisitor
import java.util.*


class LintTestDetector : Detector(), Detector.UastScanner {
    companion object {
        private const val MAX_COUNT = 3 //最大回溯次数
        private const val DEBUG = true
        private const val WLOG = "com.jyn.common.Utils.MLog"

        val ISSUE = Issue.create(
            "TestDetectorError",
            "这是一个测试Lint",
            "这是一个测试Lint",
            Category.CORRECTNESS,
            8,
            Severity.WARNING,
            Implementation(LintTestDetector::class.java, Scope.JAVA_FILE_SCOPE)
        )

        @JvmStatic
        fun sout(msg: String? = null) {
            if (!DEBUG) return
            msg?.let { print(it) } ?: println()
        }
    }

    private var rootNode: UElement? = null

    //形参所属的方法名
    private var methodName: String? = null

    //形参方法所属的类名
    private var qualifiedName: String? = null

    override fun getApplicableMethodNames(): List<String> {
        return Collections.singletonList("log")
    }

    /**
     * 检测
     */
    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        if (!context.evaluator.isMemberInClass(method, WLOG)) {
            return
        }
        ReportUtil.context = context

        sout("\n-----------------------------------\n\n")

        //遍历参数体
        node.valueArguments.last().sourcePsi
            ?.apply { sout("原文 -> ${this.text}\n") }
            ?.children
            ?.forEach {
                methodName = null
                qualifiedName = null
                this.rootNode = node
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

        sout("【${psiElement.text}】 -> ")

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
    private fun checkLiteral(uLiteral: ULiteralExpression, count: Int) {
        sout("END")
    }

    /**
     * 回溯方法类型
     */
    private fun resolveCall(uCall: UCallExpression, count: Int) {
        val uElement: UElement? = uCall.resolveToUElement() //回溯至方法定义处UElement
//        sout(" \n\t${uElement?.sourcePsi?.text}\n")
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
    }

    /**
     * 回溯变量类型
     */
    private fun resolveVariable(uReference: UReferenceExpression, count: Int) {
        val uElement = uReference.resolveToUElement() //回溯至变量初始化时UElement
        if (uElement?.sourcePsi?.text.isNullOrBlank()) return
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
                sout("${uElement.text} -> ")

                //提取方法体
                val uMethod = uReference.getParentOfType(UMethod::class.java, false)

                //获取形参index
//                uElement.parameterIndex() // 该方法不知道为什么会报错，因此换成通过字符串匹配来获取index
                val index = uMethod?.parameterList?.parameters
                    ?.indexOfFirst {
                        it.text.equals(uElement.text)
                    }
                methodName = uMethod?.name
                qualifiedName = uMethod?.containingClass?.qualifiedName

                sout("方法名：${qualifiedName}.${methodName}, index：${index}")
            }
        }
    }

    private fun check(target: String?): Boolean {
        sout(" check【$target】 -> ")
        if (target.isNullOrBlank()) return false
        val isMatch = Regex("chat.?id|user.?id", RegexOption.IGNORE_CASE).containsMatchIn(target)
        if (isMatch) {
            sout("【==> 匹配成功 <==】")
            ReportUtil.report(ISSUE, this.rootNode, "$target -> Log有问题哦，请改正！！！")
            return true
        }
        return false
    }

    override fun getApplicableUastTypes(): List<Class<out UElement>>? {
        return listOf(UCallExpression::class.java)
    }

    override fun createUastHandler(context: JavaContext): UElementHandler {
        return object : UElementHandler() {
            override fun visitCallExpression(node: UCallExpression) {
                if (node.methodName != methodName || node.methodName.isNullOrBlank()) {
                    return
                }
                sout("===> 访问Call【${node.methodName}】-> ${node.sourcePsi?.text}\n")
            }
        }
    }

//    object LogCallVisitor : AbstractUastVisitor() {
//        override fun visitCallExpression(node: UCallExpression): Boolean {
//            sout("===> 访问Call【${node.methodName}】-> ${node.sourcePsi?.text}\n")
//            return super.visitCallExpression(node)
//        }
//    }
//
//    class LogVisitor : AbstractUastVisitor() {
//        override fun visitMethod(node: UMethod): Boolean {
//            sout(" 访问Method【${node.name}】\n")
//            return super.visitMethod(node)
//        }
//
//        override fun visitCallExpression(node: UCallExpression): Boolean {
//            sout("===> 访问Call【${node.methodName}】-> ${node.sourcePsi?.text}\n")
//            return super.visitCallExpression(node)
//        }
//    }
}