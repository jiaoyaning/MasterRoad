package com.jyn.lint

import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.intellij.psi.PsiElement
import org.jetbrains.uast.UElement

/**
 * Created by jiaoyaning on 2022/11/25.
 */
object ReportUtil {
    var context: JavaContext? = null

    fun report(issue: Issue, element: UElement?, message: String) {
        context?.report(issue, element, context!!.getLocation(true), message)
    }

    fun report(issue: Issue, element: PsiElement?, message: String) {
        context?.report(issue, element, context!!.getLocation(true), message)
    }
}