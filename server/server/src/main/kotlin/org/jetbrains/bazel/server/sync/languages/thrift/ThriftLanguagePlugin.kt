package org.jetbrains.bazel.server.sync.languages.thrift

import org.jetbrains.bazel.info.BspTargetInfo
import org.jetbrains.bazel.server.dependencygraph.DependencyGraph
import org.jetbrains.bazel.server.label.label
import org.jetbrains.bazel.server.paths.BazelPathsResolver
import org.jetbrains.bazel.server.sync.languages.LanguagePlugin
import org.jetbrains.bsp.protocol.BuildTarget
import java.net.URI

class ThriftLanguagePlugin(private val bazelPathsResolver: BazelPathsResolver) : LanguagePlugin<ThriftModule>() {
  override fun dependencySources(targetInfo: BspTargetInfo.TargetInfo, dependencyGraph: DependencyGraph): Set<URI> {
    val transitiveSourceDeps =
      dependencyGraph
        .transitiveDependenciesWithoutRootTargets(targetInfo.label())
        .filter(::isThriftLibrary)
        .flatMap(BspTargetInfo.TargetInfo::getSourcesList)
        .map(bazelPathsResolver::resolveUri)
        .toHashSet()

    val directSourceDeps = sourcesFromJvmTargetInfo(targetInfo)

    return transitiveSourceDeps + directSourceDeps
  }

  private fun sourcesFromJvmTargetInfo(targetInfo: BspTargetInfo.TargetInfo): HashSet<URI> =
    if (targetInfo.hasJvmTargetInfo()) {
      targetInfo
        .jvmTargetInfo
        .jarsList
        .flatMap { it.sourceJarsList }
        .map(bazelPathsResolver::resolveUri)
        .toHashSet()
    } else {
      HashSet()
    }

  private fun isThriftLibrary(target: BspTargetInfo.TargetInfo): Boolean = target.kind == THRIFT_LIBRARY_RULE_NAME

  override fun applyModuleData(moduleData: ThriftModule, buildTarget: BuildTarget) {}

  companion object {
    private const val THRIFT_LIBRARY_RULE_NAME = "thrift_library"
  }
}
