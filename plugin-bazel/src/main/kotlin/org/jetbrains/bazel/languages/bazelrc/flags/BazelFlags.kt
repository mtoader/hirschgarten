package org.jetbrains.bazel.languages.bazelrc.flags

import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.toPersistentMap
import java.lang.reflect.Field

object BazelFlags {
  internal val declaredFieldsMap by object : LazyExtension<PersistentMap<String, Field>, BazelFlags>() {
    override fun initValue(o: BazelFlags): PersistentMap<String, Field> =
      FlagsBazel741::class.java.declaredFields
        .filter { it.getDeclaredAnnotation(Option::class.java) != null }
        .associateBy { it.name }
        .toPersistentMap()
  }

  internal val allFlags: PersistentMap<String, Flag>
    get() =
      declaredFieldsMap
        .values
        .mapNotNull {
          it.getDeclaredAnnotation(Option::class.java)?.let { op -> Pair(it, op) }
        }.mapNotNull { (f, op) -> (f.get(FlagsBazel741) as? Flag)?.let { flag -> Pair(flag, op) } }
        .flatMap { knownFlagNames(it) }
        .toMap()
        .toPersistentMap()
}

object BazelCommands {
  operator fun get(index: String): Set<String>? {
    return emptySet()
  }

//  operator fun set(index: String, value: String) {
//    data[index] = value
//  }
}
