# Codex for OSS application brief

This is an evidence-backed draft for the maintainer. Adapt it to the exact questions in the live application form; do not submit figures without rechecking them.

## Project

- **Name:** StateLayout
- **Repository:** https://github.com/wangyuyan666/StateLayout
- **License:** Apache License 2.0
- **Purpose:** A lightweight Android View container for content, empty, error, and loading states.
- **Primary maintainer:** wangyuyan666

## Evidence snapshot

Checked on 2026-09-16 before this modernization branch is published:

- Repository created in 2016.
- 48 GitHub stars and 7 forks.
- Contributions from more than one developer and two historically merged community pull requests.
- The last commit on the public default branch was dated 2017-03-17.
- The old Bintray/JCenter release path no longer works; a Maven Central successor is being prepared.

Recheck all public metrics immediately before submitting because they can change.

## Suggested project description

StateLayout is a focused Apache-2.0 Android library for applications that still use XML layouts and the classic View system. It provides one container for content, empty, loading, and error UI states. The project has historical adoption and external contributions, but its build and distribution infrastructure became obsolete after JCenter and Bintray were retired. The current maintenance effort restores reproducible builds, tests, CI, documentation, contributor workflows, and a modern release path while preserving the established Java API and Android compatibility.

## Why Codex would help

Codex would be used for maintainership work that is valuable but time-intensive:

1. Analyze old Android build behavior and propose small, reviewable migrations.
2. Generate and refine regression tests around state transitions, XML inflation, restoration, and compatibility.
3. Triage reproducible issue reports and prepare narrowly scoped fixes for maintainer review.
4. Review dependency updates and CI failures without automatically merging or releasing them.
5. Keep Java/Kotlin examples, API documentation, changelogs, and migration guidance synchronized.
6. Assist with release checklists and validate artifacts in clean consumer projects.

Human maintainers remain responsible for API decisions, security reports, merges, credentials, signatures, and releases.

## Measurable six-month outcomes

- Publish a verified `1.1.0` release through Maven Central.
- Keep the default-branch build, tests, and lint checks green.
- Add regression tests for every accepted behavior fix.
- Triage new actionable issues within a documented maintenance window.
- Label and document small contribution opportunities for new contributors.
- Publish release notes and a compatibility assessment for every release.
- Measure and report real download, issue, and contributor activity instead of estimating it.

## Evidence now present in the repository

- Reproducible Gradle wrapper with checksum validation.
- Current Android build configuration using Google Maven and Maven Central.
- Automated tests and zero-issue Android lint reports.
- GitHub Actions CI and Dependabot configuration.
- XML state-view configuration and state restoration.
- Verified local AAR, sources JAR, documentation JAR, Gradle metadata, and POM publication.
- README, changelog, roadmap, contribution guide, code of conduct, security policy, issue templates, and release procedure.

## Remaining work before submitting

1. Review the modernization changes in a pull request rather than pushing an unreviewed bulk change to the default branch.
2. Enable private vulnerability reporting and GitHub Discussions if those links are retained.
3. Verify the Maven Central namespace and configure signing and Central Portal credentials in protected GitHub environments.
4. Publish and consume `1.1.0` from a clean external project.
5. Resolve or close the historical XML-configuration issue with a public explanation.
6. Collect several weeks of genuine maintenance activity and community feedback.
7. Replace this snapshot with current metrics and link to the successful CI run and release.

## Claims to avoid

- Do not describe `1.1.0` as published until Maven Central resolves it.
- Do not claim recent active users, download counts, response times, or companies without evidence.
- Do not imply that the Codex for OSS program has accepted the project or that any unofficial eligibility criterion is guaranteed.
