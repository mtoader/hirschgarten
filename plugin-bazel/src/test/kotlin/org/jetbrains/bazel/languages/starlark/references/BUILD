load("@//rules/testing:intellij.bzl", "kt_intellij_junit4_test")
load("//plugin-bazel/src/test/kotlin/org/jetbrains/bazel/ideStarter:tests.bzl", "ide_starter_test")

kt_intellij_junit4_test(
    name = "StarlarkArgumentReferenceTest",
    src = "StarlarkArgumentReferenceTest.kt",
    data = ["//plugin-bazel/src/test/testData/starlark/references"],
    deps = [
        "//plugin-bazel",
        "//plugin-bazel/src/test/kotlin/org/jetbrains/bazel/languages/starlark/fixtures",
    ],
)

kt_intellij_junit4_test(
    name = "StarlarkAssignmentReferenceTest",
    src = "StarlarkAssignmentReferenceTest.kt",
    data = ["//plugin-bazel/src/test/testData/starlark/references"],
    deps = [
        "//plugin-bazel",
        "//plugin-bazel/src/test/kotlin/org/jetbrains/bazel/languages/starlark/fixtures",
    ],
)

ide_starter_test(
    name = "ExternalRepoResolveTest",
    size = "large",
    src = "ExternalRepoResolveTest.kt",
    classname = "org.jetbrains.bazel.languages.starlark.references.ExternalRepoResolveTest",
    tags = ["manual"],
    deps = [
        "//plugin-bazel/src/test/kotlin/org/jetbrains/bazel/ideStarter:baseTest",
    ],
)

kt_intellij_junit4_test(
    name = "StarlarkClassnameReferenceTest",
    src = "StarlarkClassnameReferenceTest.kt",
    deps = ["//plugin-bazel"],
)
