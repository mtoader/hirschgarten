package org.jetbrains.bazel.languages.bazelrc.documentation

import com.intellij.lang.documentation.DocumentationProviderEx
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiManager

class BazelrcDocumentationProvider: DocumentationProviderEx() {

  override fun getDocumentationElementForLookupItem(
    psiManager: PsiManager?,
    `object`: Any?,
    element: PsiElement?
  ): PsiElement? {
    return super.getDocumentationElementForLookupItem(psiManager, `object`, element)
  }
}
