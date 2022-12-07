package com.jyn.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
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
//            MethodTestDetector.ISSUE,
            HandlerTestDetector.ISSUE
        )

    override val vendor: Vendor
        get() = Vendor(
            vendorName = "jiaoyaning Test Lint",
            feedbackUrl = "https://github.com/jiaoyaning/MasterRoad/issues/new",
            contact = "https://github.com/jiaoyaning/MasterRoad"
        )


    override val api: Int
        get() = CURRENT_API

    override val minApi: Int
        get() = 8
}