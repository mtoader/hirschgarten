package org.jetbrains.bazel.languages.bazelrc.completion

import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.markdown.utils.doc.DocMarkdownToHtmlConverter
import com.intellij.model.Pointer
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.text.HtmlChunk.body
import com.intellij.openapi.util.text.HtmlChunk.br
import com.intellij.openapi.util.text.HtmlChunk.head
import com.intellij.openapi.util.text.HtmlChunk.html
import com.intellij.openapi.util.text.HtmlChunk.raw
import com.intellij.openapi.util.text.HtmlChunk.text
import com.intellij.platform.backend.documentation.DocumentationResult
import com.intellij.platform.backend.documentation.DocumentationSymbol
import com.intellij.platform.backend.documentation.DocumentationTarget
import com.intellij.platform.backend.presentation.TargetPresentation
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf

var flagsByName = mutableMapOf<String, BazelFlag<*>>()

open class BazelFlag<T>(
  val name: String,
  val description: String,
  val default: T? = null,
  val tags: ImmutableSet<String> = persistentSetOf(),
  val variants: ImmutableSet<T> = persistentSetOf(),
) {
  init {
    flagsByName["--$name"]?.let {
      println("$name -> $it")
    }
    flagsByName["--$name"] = this
  }

  fun getDocumentationSymbol(project: Project) = Pointer<DocumentationSymbol> {
    BazelFlagDocumentationSymbol(project, this)
  }

  open fun addLookupElements(resultSet: CompletionResultSet, project: Project) {
    resultSet.addElement(
      LookupElementBuilder.create(
        getDocumentationSymbol(project),
        name,
      ),
    )
  }

  companion object {
    fun allFlags(): Map<String, BazelFlag<*>> = flagsByName

    fun boolean(
      name: String,
      description: String,
      default: Boolean? = true,
      tags: ImmutableSet<String> = persistentSetOf(),
    ): BazelFlag<Boolean> = object : BazelFlag<Boolean>(name, description, default, tags, persistentSetOf(true, false)) {
      override fun addLookupElements(resultSet: CompletionResultSet, project: Project) {
        val docSymbol = getDocumentationSymbol(project)

        resultSet.addElement(LookupElementBuilder.create(docSymbol, name))
        resultSet.addElement(LookupElementBuilder.create(docSymbol, "no${name}"))
      }
    }
  }

  class BazelFlagDocumentationSymbol(val project: Project, val flag: BazelFlag<*>) : DocumentationSymbol {

    override fun createPointer(): Pointer<BazelFlagDocumentationSymbol> {
      return Pointer<BazelFlagDocumentationSymbol> {
        BazelFlagDocumentationSymbol(project, flag)
      }
    }

    override fun getDocumentationTarget(): DocumentationTarget {
      return FlagDocumentationTarget(this)
    }
  }

  class FlagDocumentationTarget(symbol: BazelFlagDocumentationSymbol) : DocumentationTarget {
    val symbolPtr = symbol.createPointer()

    override fun computePresentation(): TargetPresentation {
      return symbolPtr.dereference()?.let {
        return TargetPresentation.builder(it.flag.name).locationText(it.flag.tags.toString()).presentation()
      } ?: TargetPresentation.builder("").presentation()
    }

    override fun createPointer(): Pointer<DocumentationTarget> {
      return Pointer<DocumentationTarget> {
        symbolPtr.dereference()?.documentationTarget
      }
    }

    override fun computeDocumentation(): DocumentationResult? = symbolPtr.dereference()?.run {
      DocumentationResult.asyncDocumentation {
        val html = html().children(
          text(flag.name).bold().wrapWith(head()),
          br(),
          body().child(
            raw(DocMarkdownToHtmlConverter.convert(project, preprocessDescription(flag.description))),
          ),
        )

        DocumentationResult.documentation(html.toString())
      }
    }

    companion object {
      val reFlag = Regex("""(?<=\s)(--[\S=]+)(?=[\s$\.])""")
      val reFlagQuoted = Regex("""(?<=\s)('--['\S=]+')(?=[\s$\.])""")
      val reNewLine = Regex("""(?<!\n)\n+""")

      fun preprocessDescription(description: String): String = description
        .replace(reNewLine, "")
        .replace(reFlag) { "`${it.groups[1]?.value}`" }
        .replace(reFlagQuoted) { "`${it.groups[1]?.value}`" }
    }
  }
}

