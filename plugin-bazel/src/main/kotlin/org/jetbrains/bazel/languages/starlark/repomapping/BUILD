load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "repomapping",
    srcs = glob([
        "*.kt",
    ]),
    visibility = ["//plugin-bazel:__subpackages__"],
    deps = [
        "//commons/src/main/kotlin/org/jetbrains/bazel/label",
        "//plugin-bazel/src/main/kotlin/org/jetbrains/bazel/config",
        "//plugin-bazel/src/main/kotlin/org/jetbrains/bazel/sync",
        "//plugin-bazel/src/main/kotlin/org/jetbrains/bazel/sync/task",
        "//rules_intellij/intellij_platform_sdk:plugin_api",
    ],
)
