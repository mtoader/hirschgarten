package org.jetbrains.bazel.languages.bazelrc.documentation

import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.lang.documentation.ide.IdeDocumentationTargetProvider
import com.intellij.openapi.editor.Editor
import com.intellij.platform.backend.documentation.DocumentationTarget
import com.intellij.psi.PsiFile

/*
SymbolDocumentationTargetProvider and register it as com.intellij.platform.backend.documentation.symbolTargetProvider extension point to build documentation for Symbols by overriding documentationTarget()
 */
class BazelrcIdeDocumentationProvider : IdeDocumentationTargetProvider {
   override fun documentationTargets(
    editor: Editor,
    file: PsiFile,
    offset: Int
  ): List<DocumentationTarget> {
    TODO("Not yet implemented")
  }

  override fun documentationTarget(editor: Editor, file: PsiFile, lookupElement: LookupElement): DocumentationTarget? {
    return super.documentationTarget(editor, file, lookupElement)
  }
}
