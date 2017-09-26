package org.jetbrains.fortran.ide.typing

import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase
import org.jetbrains.fortran.FortranFileType
import com.intellij.codeInsight.highlighting.BraceMatchingUtil.getMatchedBraceOffset
import org.intellij.lang.annotations.Language

class FortranBraceMatcherTest : LightPlatformCodeInsightFixtureTestCase() {
    fun testPar() = doMatch("""
             program a
             integer :: i
             do i=1,4
                write<caret>(*,*) i
             enddo
             end
             """, ")"
    )

    fun testProgram() = doMatch("""
             <caret>program a
             integer :: i
             do i=1,4
                write(*,*) i
             enddo
             end
             """, "end"
    )

    fun testDo() = doMatch("""
             program a
             integer :: i
             <caret>do i=1,4
                write(*,*) i
             enddo
             end
             """, "enddo"
    )

    fun testLabeledDo() = doMatch("""
             program a
             integer :: i
             <caret>do 10 i=1,4
                write(*,*) i
          10 enddo
             end
             """, "enddo"
    )

    fun testIf() = doMatch("""
             program a
             integer :: i
             if (2 > 1) <caret>then
                 if (3 > 2) write(*,*) "a"
             else if (3>2) then
                 write(*,*) "aa"
             endif
             end
             """, "else"
    )

    fun testForall() = doMatch("""
             program a
             integer :: i
             <caret>forall (i=1:3)
             endforall
             end
             """, "endforall"
    )

    private fun doMatch(@Language("Fortran") source: String, coBrace: String) {
        myFixture.configureByText(FortranFileType, source)
        assertEquals(
                getMatchedBraceOffset(myFixture.editor, true, myFixture.file),
                source.replace("<caret>", "").lastIndexOf(coBrace))
    }
}