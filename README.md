# PLAYNOVA 🎮

PLAYNOVA is a premium **offline Android mini-games app**, using the approved PLAYNOVA Premium v2 dashboard and the upgraded playable **TRAP IQ** game (10 levels).

## How the Android APK is built

GitHub Actions automatically builds a **debug APK** when the Android code changes.

1. Open [GitHub Actions](https://github.com/EmmieConfig/PLAYNOVA/actions).
2. Open **Build PLAYNOVA Android APK**.
3. When a build shows a green check, open the run.
4. Under **Artifacts**, download `PLAYNOVA-debug-APK`, unzip it and install `app-debug.apk` on Android.

The debug APK is for testing, **not** a Play Store production release. The APK is not ready until GitHub finishes a successful build.

## What's inside?

- `app/src/main/assets/index.html` — original PLAYNOVA Premium v2 dashboard with TRAP IQ bundled, works offline.
- `app/src/main/java/com/playnova/arcade/MainActivity.java` — Java Android WebView app.
- `app/src/main/res/drawable/playnova_icon.xml` — PLAYNOVA app launcher icon.
- `.github/workflows/android-apk.yml` — GitHub Actions Android build.
- `app/build.gradle` — Android app build configuration.

## Current game availability

- **TRAP IQ**: 10 playable platform/trap levels, pause/resume, louder sound effects, and Relaxed/Normal/Fast movement settings controlled centrally from PLAYNOVA Home → Settings.
- Other categories/games shown in the hub are planned/previews and are not yet playable.
- Favorites and basic profile/progress are stored on the device using WebView local storage.

## No local developer software required

The user does not need to install Android Studio or run build commands. GitHub Actions builds the APK in the cloud. Physical Android device testing is still required before calling the app production-ready.

## PLAYNOVA Settings (v1.1.1)

Open the gear on the Home screen or use the bottom Settings tab. Sound effects can be toggled, effects volume adjusted from 0–100% (90% default), a sound preview played, and gameplay movement speed set to Relaxed, Normal or Fast. Preferences save locally and apply when launching TRAP IQ. The in-game Pause menu no longer contains settings. Device media-volume also affects audible loudness.
