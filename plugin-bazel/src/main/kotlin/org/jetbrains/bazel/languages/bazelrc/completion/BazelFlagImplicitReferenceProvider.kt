package org.jetbrains.bazel.languages.bazelrc.completion

import com.intellij.model.Symbol
import com.intellij.model.psi.ImplicitReferenceProvider
import com.intellij.model.psi.PsiSymbolReference
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import org.jetbrains.bazel.languages.bazelrc.psi.BazelrcFlag

class BazelFlagImplicitReferenceProvider: ImplicitReferenceProvider {
  override fun getImplicitReference(
    element: PsiElement,
    offsetInElement: Int
  ): PsiSymbolReference? = (element as? BazelrcFlag)?.let(::BazelrcFlagReference)
}

class BazelrcFlagReference(val psiFlag: BazelrcFlag): PsiSymbolReference {
  override fun getElement(): PsiElement {
    return psiFlag
  }

  override fun getRangeInElement(): TextRange {
    return TextRange(0, psiFlag.textLength)
  }

  override fun resolveReference(): Collection<Symbol?> {
    return BazelFlag.allFlags()[element.text]?.let {
      it.getDocumentationSymbol(psiFlag.project).dereference().let(::listOf)
    } ?: emptyList()
  }
}
