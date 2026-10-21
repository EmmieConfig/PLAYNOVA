# LUDO ROYALE — Locked Board Specification (v0.6)

**Status:** Single final board design, approved by the user on 2026-10-21. Do not alter, redraw, recolor, regenerate, reframe or add alternative themes without explicit new approval.

## Authoritative visual source
- The original generated 1254 × 1254 PNG ("top-down ultra-polished high-detail 3D" royal board) is the sole design reference.
- PNG SHA-256: `748bf93941655ce5b76148a449748f298987f044ee54386aa9f235340245b86d`.
- The same exact RGB pixels are embedded into `LUDO_ROYALE_v0.6_LOCKED.html` as **lossless WebP**. Pixel comparison against the original PNG was verified identical before embedding. No redrawing or redesign of the game-board imagery.
- Board pictured as blue top-left, green top-right, red bottom-left, yellow bottom-right, with glossy tokens, cinematic black tabletop, neon trim and 8 visible safe stars. Each player's 4 original pawns remain perfectly centered in the source artwork before game starts.

## Rules/geometry
- The 8 protected track indices: `[0,8,13,21,26,34,39,47]`. These are the four colored start squares plus four neutral safe squares in the original 15 × 15 layout.
- The photographic source uses hand-measured perspective cell boundaries; all live token hitboxes and path animation use those mapped coordinates.
- Do not draw a second set of pawns over baked source pawns. On departure from a home slot, cover *only* that token with an empty socket. Restore the original slot as soon as captured pawn returns to base.
- Keep existing dice physics, AI, local multiplayer, Standard/Custom rules, capture animations, sound and pause functionality.

## Scope
- Only the standalone Ludo Royale browser game is updated, under branch `ludo-royale-preview`.
- Do **not** integrate into main PLAYNOVA Android app before the user reviews and explicitly approves this standalone build.
