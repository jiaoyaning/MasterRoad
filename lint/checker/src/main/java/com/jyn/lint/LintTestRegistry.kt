package com.jyn.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue

class LintTestRegistry : IssueRegistry() {
    companion object {
        init {
            println("******************************************************")
            println("************** Lint Test 开始静态分析代码 ***************")
            println("******************************************************")
        }
    }

    override val issues: List<Issue>
        get() = listOf(
//            LintTestDetector.ISSUE,
            LintTestVisitor.ISSUE
        )

    override val api: Int
        get() = CURRENT_API

    override val minApi: Int
        get() = 8
}