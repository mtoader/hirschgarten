load("//plugin-bazel/src/test/kotlin/org/jetbrains/bazel/ideStarter:tests.bzl", "ide_starter_test")

ide_starter_test(
    name = "testRunLineMarker",
    size = "large",
    src = "RunLineMarkerTest.kt",
    classname = "org.jetbrains.bazel.ui.testRunLineMarker.RunLineMarkerTest",
    tags = ["manual"],
    deps = [
        "//plugin-bazel/src/test/kotlin/org/jetbrains/bazel/ideStarter:baseTest",
    ],
)
