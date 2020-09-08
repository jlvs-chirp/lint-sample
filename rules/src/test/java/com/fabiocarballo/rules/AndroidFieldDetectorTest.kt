package com.fabiocarballo.rules

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
import org.junit.jupiter.api.Test

class AndroidFieldDetectorTest: LintDetectorTest() {
    override fun getDetector(): Detector = AndroidFieldDetector()

    override fun getIssues(): MutableList<Issue> = mutableListOf(AndroidFieldDetector.DETECT_FIELD)

    @Test
    fun shouldDetectUsageOfFiel() {
        val stubFile = java("src/test/pkg/FieldDetectorTests.java",
            """
            package test.pkg;
            
            class BaseClass {
                public static int x;
            }

            class Subclass extends BaseClass {}

            public class FieldDetectorTests {
                public void testMethod() {
                    final int x = Subclass.x;
            }
}
            """.trimIndent()
        ).indented()

        val lintResult = lint()
            .files(Stubs.ANDROID_LOG_IMPL_JAVA, stubFile)
            .run()

        lintResult
            .expectErrorCount(1)
            .expect(
                """
             src/test/pkg/FieldDetectorTests.java:11: Error: Found x [DetectField]
                                 final int x = Subclass.x;
                                                        ~
             1 errors, 0 warnings
         """.trimIndent()
            )
    }
}