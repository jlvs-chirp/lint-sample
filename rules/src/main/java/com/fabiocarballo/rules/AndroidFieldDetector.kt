package com.fabiocarballo.rules

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiField
import org.jetbrains.uast.UReferenceExpression


class AndroidFieldDetector: Detector(), SourceCodeScanner {

    override fun getApplicableReferenceNames(): List<String>? {
        return listOf("x")
    }

    override fun visitReference(
        context: JavaContext,
        reference: UReferenceExpression,
        referenced: PsiElement
    ) {
        val field = referenced as PsiField

        val message =
            "Found " + field.getName()
                .toString()
        context.report(AndroidFieldDetector.DETECT_FIELD, context.getLocation(reference), message);
    }

    companion object {
        val IMPLEMENTATION =
            Implementation(
                AndroidFieldDetector::class.java, Scope.JAVA_FILE_SCOPE
            )
        val DETECT_FIELD: Issue = Issue.create(
            "DetectField",
            "Find field by name",
            "Find field by name",
            Category.CORRECTNESS, 6, Severity.ERROR,
            this.IMPLEMENTATION
        )
    }
}