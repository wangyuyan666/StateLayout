# Changelog

Notable changes follow [Keep a Changelog](https://keepachangelog.com/en/1.1.0/) and releases use [Semantic Versioning](https://semver.org/).

## [Unreleased]

### Added

- XML attributes for assigning content, empty, error, and loading views and an initial state.
- State persistence across Android view recreation.
- Robolectric behavior tests, Android lint, and GitHub Actions CI.
- Local Maven publication with sources, documentation, licensing, and SCM metadata.
- Contributor, security, conduct, and release documentation.

### Changed

- Modernized the build to Gradle 8.14.5, Android Gradle Plugin 8.13.2, AndroidX annotations, and SDK 36.
- Replaced the retired JCenter and Bintray infrastructure with Google Maven and Maven Central repositories.
- The sample targets Android 16 while the library retains minSdk 8 compatibility.

### Fixed

- Replacing a programmatically supplied state view now removes the prior managed view.
- Invalid state values and missing referenced view IDs now fail with actionable errors.
- The sample's second activity is registered in the manifest.

## [1.0.3] - 2017-03-17

- Historical Bintray/JCenter release. Its original artifact is no longer available from Maven Central.

[Unreleased]: https://github.com/wangyuyan666/StateLayout/compare/1.0.3...HEAD
[1.0.3]: https://github.com/wangyuyan666/StateLayout/tree/6acfafe
