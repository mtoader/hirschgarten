package org.jetbrains.bazel.languages.bazelrc.completion

import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
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
    println("haha $name $description")
    flagsByName[name]?.let {
      println("$name -> $it")
    }
    flagsByName[name] = this
  }

  open fun addLookupElements(resultSet: CompletionResultSet) {
    resultSet.addElement(LookupElementBuilder.create(this, name))
  }

  companion object {

    fun allFlags(): Map<String, BazelFlag<*>> = flagsByName

    fun boolean(
      name: String,
      description: String,
      default: Boolean? = true,
      tags: ImmutableSet<String> = persistentSetOf(),
    ): BazelFlag<Boolean> = object : BazelFlag<Boolean>(name, description, default, tags, persistentSetOf(true, false)) {
      override fun addLookupElements(resultSet: CompletionResultSet) {
        resultSet.addElement(LookupElementBuilder.create(this, name))
        resultSet.addElement(LookupElementBuilder.create(this, "no${name}"))
      }
    }
  }
}

