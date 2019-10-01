## 1. Hello world

1. Create a plain Android App project. (target version is Android 6). All work must be performed inside git repository.
2. Set-up a basic app versioning. Application package should have proper version code and version name according to SEMVER (Semantic Versioning). Version name should contain major, minor and patch version digits. Version tracking should be performed by utilizing git tag feature. Each tag should be formatted as "MAJOR.MINOR" (e.g. 1.3), PATCH number represents a number of commits performed after latest release (latest tag).
3. Resulting **release** APK should be signed with personal certificate automatically during build.
4. The only activity of application should display current version name and device ID (which is secured by corresponding runtime permission).
5. You should properly handle runtime permission and give user a dummy explanation why it is necessary to grant this permission. App should withstand screen rotation and other possible test-cases.