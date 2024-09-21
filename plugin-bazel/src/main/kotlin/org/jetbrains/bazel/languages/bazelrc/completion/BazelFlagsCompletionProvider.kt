package org.jetbrains.bazel.languages.bazelrc.completion

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.util.ProcessingContext
import org.jetbrains.bazel.languages.bazelrc.BazelrcLanguage
import org.jetbrains.bazel.languages.bazelrc.elements.BazelrcElementTypes

class BazelFlagsCompletionProvider : CompletionProvider<CompletionParameters>() {
  override fun addCompletions(
    parameters: CompletionParameters,
    context: ProcessingContext,
    result: CompletionResultSet,
  ) {
    BazelFlag.allFlags().values.map { it.addLookupElements(result) }
  }

  companion object {
    val psiPattern =
      psiElement().withLanguage(BazelrcLanguage).andOr(
        psiElement(BazelrcElementTypes.FLAG),
        psiElement().atStartOf(psiElement(BazelrcElementTypes.FLAG)),
      )
  }

  /**
   * object BazelFlag {
   *   @JvmStatic fun runUnder(command: String) = arg("run_under", command)
   *
   *   @JvmStatic fun color(enabled: Boolean) = arg("color", if (enabled) "yes" else "no")
   *
   *   @JvmStatic fun keepGoing() = flag("keep_going")
   *
   *   @JvmStatic fun outputGroups(groups: List<String>) = arg("output_groups", groups.joinToString(","))
   *
   *   @JvmStatic fun aspect(name: String) = arg("aspects", name)
   *
   *   @JvmStatic fun buildManualTests(): String = flag("build_manual_tests")
   *
   *   // Use file:// uri scheme for output paths in the build events.
   *   @JvmStatic fun buildEventBinaryPathConversion(enabled: Boolean): String =
   *     arg(
   *       "build_event_binary_file_path_conversion",
   *       enabled.toString(),
   *     )
   *
   *   @JvmStatic fun curses(enabled: Boolean): String = arg("curses", if (enabled) "yes" else "no")
   *
   *   @JvmStatic fun repositoryOverride(repositoryName: String, path: String): String = arg("override_repository", "$repositoryName=$path")
   *
   *   @JvmStatic fun testOutputAll(): String = arg("test_output", "all")
   *
   *   @JvmStatic fun buildTagFilters(tags: List<String>): String = arg("build_tag_filters", tags.joinToString(","))
   *
   *   @JvmStatic fun experimentalGoogleLegacyApi(): String = flag("experimental_google_legacy_api")
   *
   *   @JvmStatic fun experimentalEnableAndroidMigrationApis(): String = flag("experimental_enable_android_migration_apis")
   *
   *   @JvmStatic fun device(device: String): String = arg("device", device)
   *
   *   @JvmStatic fun start(startType: String): String = arg("start", startType)
   *
   *   @JvmStatic fun testFilter(filterExpression: String): String = arg("test_filter", filterExpression)
   *
   *   @JvmStatic fun toolTag(): String = arg("tool_tag", "$NAME:$VERSION")
   *
   *   private fun arg(name: String, value: String) = String.format("--%s=%s", name, value)
   *
   *   private fun flag(name: String) = "--$name"
   * }
   */
}
