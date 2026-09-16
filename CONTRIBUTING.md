# Contributing

Thank you for helping improve StateLayout.

## Before opening a change

- Use an issue for behavior changes or new public APIs so the scope can be agreed first.
- Keep the library focused on Android View-based content, empty, loading, and error states.
- Do not include generated build output, local SDK paths, or credentials.

## Development setup

Requirements:

- JDK 17
- Android SDK Platform 36

Run the standard checks from a clean checkout:

```bash
./gradlew testDebugUnitTest lintDebug assembleDebug assembleRelease
```

Verify publication metadata with:

```bash
./gradlew publishReleasePublicationToMavenLocal
```

## Code and compatibility

- Preserve existing Java APIs unless a breaking change has been discussed and approved.
- Keep existing Java production classes in Java. Use Kotlin for new production classes unless an API or nearby pattern requires Java.
- Add tests for every behavior change.
- Register every new sample `Activity` in `app/src/main/AndroidManifest.xml`.
- Avoid raising `minSdk` without a documented user benefit and migration plan.

## Pull requests

Keep pull requests small and explain the problem, approach, compatibility impact, and verification performed. By contributing, you agree that your work is licensed under Apache License 2.0.
