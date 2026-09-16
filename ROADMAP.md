# Roadmap

## 1.1.0 — maintenance release

- [x] Restore builds on JDK 17 and a current Android toolchain.
- [x] Migrate from Support Library annotations to AndroidX.
- [x] Add XML state-view attributes and close issue #3 after maintainer review.
- [x] Add state transition and recreation tests.
- [x] Add CI and verifiable Maven publication metadata.
- [ ] Complete community review of the compatibility changes.
- [ ] Configure a Maven Central namespace and signing credentials.
- [ ] Publish the release and validate it from a clean consumer project.

## Later candidates

These require user evidence and API design discussion before implementation:

- Optional state transition listener.
- Optional state transition animations that remain accessibility-friendly.
- A separate Compose interoperability sample, without changing the View library's scope.

The project intentionally remains a small View-system primitive rather than a networking, pagination, or application state-management framework.
